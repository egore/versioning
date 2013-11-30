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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;

import de.egore911.versioning.persistence.model.VcsHost;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
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
