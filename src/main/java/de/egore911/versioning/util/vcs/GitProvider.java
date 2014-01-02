/*
 * Copyright 2013  Christoph Brill <egore911@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.egore911.versioning.util.vcs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.eclipse.jgit.api.LsRemoteCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.util.FS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.JSch;

import de.egore911.versioning.persistence.model.Project;

/**
 * Git based information provider, based on jgit.
 * 
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class GitProvider extends Provider {

	private static final Logger log = LoggerFactory
			.getLogger(GitProvider.class);

	static {
		// Wire up our logging redirection
		JSch.setLogger(new JschToSlf4j());
	}

	@Override
	public boolean tagExistsImpl(Project project, String tagName) {
		InMemoryRepository repo = initRepository(project);

		// Ask for the remote tags
		LsRemoteCommand command = new LsRemoteCommand(repo);
		command.setTags(true);
		try {
			Collection<Ref> tags = command.call();
			for (Ref tag : tags) {
				// Tag found, all is fine
				if (tag.getName().equals("refs/tags/" + tagName)) {
					return true;
				}
			}
			// Tag not found
			return false;
		} catch (GitAPIException e) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			if (facesContext != null) {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_WARN, e.getLocalizedMessage(),
						e.getLocalizedMessage());
				facesContext.addMessage("main", message);
			}
			log.error(e.getMessage(), e);
			return false;
		}
	}

	private InMemoryRepository initRepository(Project project) {
		DfsRepositoryDescription repoDesc = new DfsRepositoryDescription(
				"git - " + project.getName() + " on "
						+ project.getVcsHost().getName());
		InMemoryRepository repo = new InMemoryRepository(repoDesc) {
			@Override
			public org.eclipse.jgit.util.FS getFS() {
				// Hack the InMemoryRepository to have a valid FS, eventhough it
				// does not have one. Otherwise the LsRemoteCommand will crash
				// with a NPE

				FS fs = super.getFS();
				if (fs == null) {
					fs = FS.DETECTED;
				}
				return fs;
			}
		};

		// Mock the configuration to contain the origin url
		StoredConfig config = repo.getConfig();
		config.setString("remote", "origin", "url",
				project.getCompleteVcsPath());
		return repo;
	}

	@Override
	protected List<Tag> getTagsImpl(Project project) {
		InMemoryRepository repo = initRepository(project);
		List<Tag> result = new ArrayList<>();

		// Ask for the remote tags
		LsRemoteCommand command = new LsRemoteCommand(repo);
		command.setTags(true);
		try {
			Collection<Ref> tags = command.call();
			for (Ref tag : tags) {
				result.add(new Tag(null, tag.getName()
						.replace("refs/tags/", "")));
			}
			return result;
		} catch (GitAPIException e) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			if (facesContext != null) {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_WARN, e.getLocalizedMessage(),
						e.getLocalizedMessage());
				facesContext.addMessage("main", message);
			}
			log.error(e.getMessage(), e);
			return Collections.emptyList();
		}
	}
}
