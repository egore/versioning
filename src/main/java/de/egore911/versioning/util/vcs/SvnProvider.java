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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmatesoft.svn.core.SVNDirEntry;
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
 * Subversion based information provider, based on SVNKit.
 * 
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class SvnProvider extends Provider {

	private static final Logger log = LoggerFactory
			.getLogger(SvnProvider.class);

	@Override
	public boolean tagExistsImpl(Project project, String tagName) {
		try {
			SVNRepository repo = initRepository(project);

			String list = getTagsDir(project);

			// Check if the tag exists by verifying it's a directory
			SVNNodeKind checkPath = repo.checkPath(list + "/" + tagName,
					repo.getLatestRevision());
			return checkPath == SVNNodeKind.DIR;
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

	private String getTagsDir(Project project) {
		String list = "tags";
		String completeVcsPath = project.getCompleteVcsPath();
		int indexOf = completeVcsPath.indexOf("/trunk/");
		if (indexOf >= 0) {
			list = "tags"
					+ completeVcsPath
							.substring(indexOf + "/trunk/".length());
		} else {
			indexOf = completeVcsPath.indexOf("/trunk");
			if (indexOf >= 0) {
				list = "tags"
						+ completeVcsPath
								.substring(indexOf + "/trunk".length());
			}
		}
		return list;
	}

	private SVNRepository initRepository(Project project) throws SVNException {
		String completeVcsPath = project.getCompleteVcsPath();
		SVNURL svnurl;
		if (!completeVcsPath.contains("/trunk")) {
			// If the URL does not contain trunk anywhere, assume that
			// trunk/tags/branches are the folder right below the
			// completeVcsPath

			svnurl = SVNURL.parseURIEncoded(completeVcsPath);
		} else {
			// If the URL contains trunk remove everything starting from it
			// so we get the folder above, as the tags folder is located at
			// this level

			svnurl = SVNURL.parseURIEncoded(completeVcsPath.replaceAll(
					"/trunk.*", ""));
		}
		SVNRepository repo = SVNRepositoryFactory.create(svnurl);
		VcsHost vcsHost = project.getVcsHost();

		// If the VCS host has credentials available use them
		if (vcsHost.isCredentialsAvailable()) {
			ISVNAuthenticationManager authManager = new BasicAuthenticationManager(
					vcsHost.getUsername(), vcsHost.getPassword());
			repo.setAuthenticationManager(authManager);
		}
		return repo;
	}

	@Override
	protected List<Tag> getTagsImpl(Project project) {
		try {
			SVNRepository repo = initRepository(project);

			String list = getTagsDir(project);

			Collection<SVNDirEntry> entries = new ArrayList<>();
			repo.getDir(list, repo.getLatestRevision(), false, entries);
			List<Tag> result = new ArrayList<>();
			for (SVNDirEntry entry : entries) {
				result.add(new Tag(entry.getDate(), entry.getName()));
			}
			return result;
		} catch (SVNException e) {
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
