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

import de.egore911.versioning.persistence.model.VcsHost;

public class GitProvider extends Provider {

	private static final Logger log = LoggerFactory
			.getLogger(GitProvider.class);

	static {
		JSch.setLogger(new JschToSlf4j());
	}

	private final VcsHost vcsHost;

	public GitProvider(VcsHost vcsHost) {
		this.vcsHost = vcsHost;
	}

	@Override
	public boolean tagExistsImpl(String tagName) {
		DfsRepositoryDescription repoDesc = new DfsRepositoryDescription(
				"git - " + vcsHost.getName());
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
		config.setString("remote", "origin", "url", vcsHost.getUri());
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
