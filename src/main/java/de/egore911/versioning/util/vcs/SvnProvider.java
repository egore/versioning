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

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;

import de.egore911.versioning.persistence.model.Project;
import de.egore911.versioning.persistence.model.VcsHost;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class SvnProvider extends Provider {

	private static final Logger log = LoggerFactory
			.getLogger(SvnProvider.class);

	@Override
	public boolean tagExistsImpl(Project project, String tagName) {
		try {
			String completeVcsPath = project.getCompleteVcsPath();
			if (!completeVcsPath.contains("trunk")) {
				SVNURL svnurl = SVNURL.parseURIEncoded(completeVcsPath);
				SVNRepository repo = SVNRepositoryFactory.create(svnurl);
				VcsHost vcsHost = project.getVcsHost();
				if (vcsHost.isCredentialsAvailable()) {
					ISVNAuthenticationManager authManager = new BasicAuthenticationManager(
							vcsHost.getUsername(), vcsHost.getPassword());
					repo.setAuthenticationManager(authManager);
				}
				SVNNodeKind checkPath = repo.checkPath("tags/" + tagName,
						repo.getLatestRevision());
				return checkPath == SVNNodeKind.DIR;
			} else {
				SVNURL svnurl = SVNURL.parseURIEncoded(completeVcsPath.replace(
						"/trunk", "/tags"));
				SVNRepository repo = SVNRepositoryFactory.create(svnurl);
				VcsHost vcsHost = project.getVcsHost();
				if (vcsHost.isCredentialsAvailable()) {
					ISVNAuthenticationManager authManager = new BasicAuthenticationManager(
							vcsHost.getUsername(), vcsHost.getPassword());
					repo.setAuthenticationManager(authManager);
				}
				SVNNodeKind checkPath = repo.checkPath(tagName,
						repo.getLatestRevision());
				return checkPath == SVNNodeKind.DIR;
			}
		} catch (SVNException e) {
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
}
