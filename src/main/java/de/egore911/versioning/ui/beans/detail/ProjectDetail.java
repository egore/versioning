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

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

import de.egore911.versioning.persistence.dao.ProjectDao;
import de.egore911.versioning.persistence.dao.ServerDao;
import de.egore911.versioning.persistence.dao.VcshostDao;
import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.Project;
import de.egore911.versioning.persistence.model.Server;
import de.egore911.versioning.persistence.model.VcsHost;
import de.egore911.versioning.util.security.RequiresPermission;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@ManagedBean(name = "projectDetail")
@RequestScoped
@RequiresPermission(Permission.ADMIN_SETTINGS)
public class ProjectDetail extends AbstractDetail<Project> {

	@Override
	public Project getInstance() {
		if (instance == null) {
			instance = new Project();
		}
		return instance;
	}

	@Override
	protected ProjectDao getDao() {
		return new ProjectDao();
	}

	public SelectItem[] getVcshostSelectItems() {
		List<VcsHost> vcshosts = new VcshostDao().findAll();
		SelectItem[] items = new SelectItem[vcshosts.size()];
		int i = 0;
		for (VcsHost vcshost : vcshosts) {
			items[i++] = new SelectItem(vcshost, vcshost.getName());
		}
		return items;
	}

	public SelectItem[] getAllServerSelectItems() {
		List<Server> servers = new ServerDao().findAll();
		SelectItem[] items = new SelectItem[servers.size()];
		int i = 0;
		for (Server server : servers) {
			items[i++] = new SelectItem(server, server.getName());
		}
		return items;
	}

	public String save() {
		getDao().save(getInstance());
		return "/projects.xhtml";
	}

}
