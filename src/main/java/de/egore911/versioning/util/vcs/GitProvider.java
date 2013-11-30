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

import java.util.Collection;

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
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class GitProvider extends Provider {

	private static final Logger log = LoggerFactory
			.getLogger(GitProvider.class);

	static {
		JSch.setLogger(new JschToSlf4j());
	}

	@Override
	public boolean tagExistsImpl(Project project, String tagName) {
		DfsRepositoryDescription repoDesc = new DfsRepositoryDescription(
				"git - " + project.getName() + " on "
						+ project.getVcsHost().getName());
		InMemoryRepository repo = new InMemoryRepository(repoDesc) {
			@Override
			public org.eclipse.jgit.util.FS getFS() {
				FS fs = super.getFS();
				if (fs == null) {
					fs = FS.DETECTED;
				}
				return fs;
			}
		};
		StoredConfig config = repo.getConfig();
		config.setString("remote", "origin", "url",
				project.getCompleteVcsPath());
		LsRemoteCommand command = new LsRemoteCommand(repo);
		command.setTags(true);
		try {
			Collection<Ref> tags = command.call();
			for (Ref tag : tags) {
				if (tag.getName().equals("refs/tags/" + tagName)) {
					return true;
				}
			}
			return false;
		} catch (GitAPIException e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}
}
