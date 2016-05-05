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
import java.util.stream.Collectors;

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
			return entries.stream()
					.map(entry -> new Tag(entry.getDate(), entry.getName()))
					.collect(Collectors.toList());
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
