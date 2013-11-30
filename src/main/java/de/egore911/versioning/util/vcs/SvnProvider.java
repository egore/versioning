package de.egore911.versioning.util.vcs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;

import de.egore911.versioning.persistence.model.VcsHost;

public class SvnProvider extends Provider {

	private static final Logger log = LoggerFactory
			.getLogger(SvnProvider.class);

	private final VcsHost vcsHost;

	public SvnProvider(VcsHost vcsHost) {
		this.vcsHost = vcsHost;
	}

	@Override
	public boolean tagExistsImpl(String tagName) {
		try {
			SVNURL svnurl = SVNURL.parseURIEncoded(vcsHost.getUri());
			SVNRepository repo = SVNRepositoryFactory.create(svnurl);
			SVNNodeKind checkPath = repo.checkPath("tags/" + tagName,
					repo.getLatestRevision());
			return checkPath == SVNNodeKind.DIR;
		} catch (SVNException e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}
}
