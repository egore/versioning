/*
 * Copyright 2013  Christoph Brill <egore911@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
