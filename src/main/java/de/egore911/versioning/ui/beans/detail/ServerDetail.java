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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import de.egore911.versioning.persistence.dao.ProjectDao;
import de.egore911.versioning.persistence.dao.ServerDao;
import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.Project;
import de.egore911.versioning.persistence.model.Server;
import de.egore911.versioning.persistence.model.Variable;
import de.egore911.versioning.persistence.model.Version;
import de.egore911.versioning.ui.logic.DeploymentCalculator;
import de.egore911.versioning.util.SessionUtil;
import de.egore911.versioning.util.security.RequiresPermission;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@ManagedBean(name = "serverDetail")
@RequestScoped
@RequiresPermission(Permission.ADMIN_SETTINGS)
public class ServerDetail extends AbstractDetail<Server> {

	private final DeploymentCalculator deploymentCalculator = new DeploymentCalculator();

	@Override
	protected ServerDao getDao() {
		return new ServerDao();
	}

	@Override
	protected Server createEmpty() {
		HttpSession session = new SessionUtil().getSession();
		session.setAttribute("server_" + null + "_origProjects",
				new ArrayList<>());
		return new Server();
	}

	@Override
	protected Server load(Integer id) {
		Server server = super.load(id);
		HttpSession session = new SessionUtil().getSession();
		session.setAttribute("server_" + server.getId() + "_origProjects",
				new ArrayList<>(server.getConfiguredProjects()));
		return server;
	}

	public List<Version> getDeployedVersions() {
		if (!isManaged()) {
			return Collections.emptyList();
		}
		return deploymentCalculator.getDeployedVersions(instance);
	}

	public List<Version> getDeployableVersions() {
		if (!isManaged()) {
			return Collections.emptyList();
		}
		return deploymentCalculator.getDeployableVersions(instance);
	}

	public SelectItem[] getAllProjects() {
		List<Project> projects = new ProjectDao().findAll();
		SelectItem[] items = new SelectItem[projects.size()];
		int i = 0;
		for (Project project : projects) {
			items[i++] = new SelectItem(project, project.getName());
		}
		return items;
	}

	public String addVariable() {
		Variable variable = new Variable();
		variable.setServer(getInstance());
		getInstance().getVariables().add(variable);
		return "";
	}

	public String save() {

		if (!validate("server")) {
			return "";
		}

		if (getInstance().getName().contains("/")) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ResourceBundle bundle = sessionUtil.getBundle();
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					bundle.getString("invalid_chars_in_name"),
					bundle.getString("invalid_chars_in_name_detail"));
			facesContext.addMessage("main:server_name", message);
			return "";
		}

		for (Project project : getInstance().getConfiguredProjects()) {
			if (!project.getConfiguredServers().contains(getInstance())) {
				project.getConfiguredServers().add(getInstance());
			}
		}

		HttpSession session = new SessionUtil().getSession();
		List<Project> origProjects = (List<Project>) session
				.getAttribute("server_" + getInstance().getId()
						+ "_origProjects");
		session.setAttribute("server_" + getInstance().getId()
				+ "_origProjects", null);
		ProjectDao projectDao = new ProjectDao();
		for (Project project : origProjects) {
			if (!getInstance().getConfiguredProjects().contains(project)) {
				project = projectDao.reattach(project);
				project.getConfiguredServers().remove(getInstance());
				projectDao.save(project);
			}
		}

		getDao().save(getInstance());
		setInstance(null);
		return "/servers.xhtml";
	}

}
