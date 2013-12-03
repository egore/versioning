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
package de.egore911.versioning.ui.beans.detail;

import java.util.List;
import java.util.ResourceBundle;

import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import de.egore911.versioning.persistence.dao.ProjectDao;
import de.egore911.versioning.persistence.dao.VersionDao;
import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.Project;
import de.egore911.versioning.persistence.model.Version;
import de.egore911.versioning.util.SessionUtil;
import de.egore911.versioning.util.security.RequiresPermission;
import de.egore911.versioning.util.vcs.Provider;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@Named("versionDetail")
@ConversationScoped
@RequiresPermission(Permission.USE)
public class VersionDetail extends AbstractDetail<Version> {

	private static final long serialVersionUID = -8116891433758776459L;

	private final SessionUtil sessionUtil = new SessionUtil();

	@Inject
	private VersionDao versionDao;
	@Inject
	private ProjectDao projectDao;

	@Override
	public Version getInstance() {
		if (instance == null) {
			instance = new Version();
		}
		return instance;
	}

	@Override
	protected VersionDao getDao() {
		return versionDao;
	}

	public SelectItem[] getProjectSelectItems() {
		List<Project> projects = projectDao.findAll();
		SelectItem[] items = new SelectItem[projects.size()];
		int i = 0;
		for (Project project : projects) {
			items[i++] = new SelectItem(project, project.getName());
		}
		return items;
	}

	public String save() {
		Version version = getInstance();

		Provider provider = version.getProject().getProvider();
		if (!provider.tagExists(version.getProject(), version.getVcsTag())) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ResourceBundle bundle = sessionUtil.getBundle();
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					bundle.getString("tag_not_found"),
					bundle.getString("tag_not_found_detail"));
			facesContext.addMessage("main:user_password", message);
			return "";
		}

		getDao().save(version);
		if (!conversation.isTransient()) {
			conversation.end();
		}
		return "/versions.xhtml";
	}

}
