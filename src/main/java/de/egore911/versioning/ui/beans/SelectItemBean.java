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
package de.egore911.versioning.ui.beans;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import de.egore911.versioning.persistence.dao.MavenRepositoryDao;
import de.egore911.versioning.persistence.dao.ProjectDao;
import de.egore911.versioning.persistence.dao.RoleDao;
import de.egore911.versioning.persistence.dao.ServerDao;
import de.egore911.versioning.persistence.dao.TagTransformerDao;
import de.egore911.versioning.persistence.dao.VcshostDao;
import de.egore911.versioning.persistence.model.MavenRepository;
import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.Project;
import de.egore911.versioning.persistence.model.Role;
import de.egore911.versioning.persistence.model.Server;
import de.egore911.versioning.persistence.model.TagTransformer;
import de.egore911.versioning.persistence.model.Vcs;
import de.egore911.versioning.persistence.model.VcsHost;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@Named
@RequestScoped
public class SelectItemBean {

	@Inject
	private MavenRepositoryDao mavenRepositoryDao;
	@Inject
	private ProjectDao projectDao;
	@Inject
	private ServerDao serverDao;
	@Inject
	private TagTransformerDao tagTransformerDao;
	@Inject
	private VcshostDao vcshostDao;

	public SelectItem[] getAllProjectSelectItems() {
		List<Project> projects = projectDao.findAll();
		SelectItem[] items = new SelectItem[projects.size()];
		int i = 0;
		for (Project project : projects) {
			items[i++] = new SelectItem(project, project.getName());
		}
		return items;
	}

	public SelectItem[] getAllVcshostSelectItems() {
		List<VcsHost> vcshosts = vcshostDao.findAll();
		SelectItem[] items = new SelectItem[vcshosts.size()];
		int i = 0;
		for (VcsHost vcshost : vcshosts) {
			items[i++] = new SelectItem(vcshost, vcshost.getName());
		}
		return items;
	}

	public SelectItem[] getAllServerSelectItems() {
		List<Server> servers = serverDao.findAll();
		SelectItem[] items = new SelectItem[servers.size()];
		int i = 0;
		for (Server server : servers) {
			items[i++] = new SelectItem(server, server.getName());
		}
		return items;
	}

	public SelectItem[] getAllMavenRepositoriesSelectItems() {
		List<MavenRepository> mavenRepositories = mavenRepositoryDao.findAll();
		SelectItem[] items = new SelectItem[mavenRepositories.size()];
		int i = 0;
		for (MavenRepository mavenRepository : mavenRepositories) {
			items[i++] = new SelectItem(mavenRepository,
					mavenRepository.getName());
		}
		return items;
	}

	public SelectItem[] getAllTagTransformerSelectItems() {
		List<TagTransformer> tagTransformers = tagTransformerDao.findAll();
		SelectItem[] items = new SelectItem[tagTransformers.size()];
		int i = 0;
		for (TagTransformer tagTransformer : tagTransformers) {
			items[i++] = new SelectItem(tagTransformer,
					tagTransformer.getName());
		}
		return items;
	}

	public SelectItem[] getAllPermissionSelectItems() {
		SelectItem[] items = new SelectItem[Permission.values().length];
		int i = 0;
		for (Permission permission : Permission.values()) {
			items[i++] = new SelectItem(permission, permission.name());
		}
		return items;
	}

	public SelectItem[] getAllRoleSelectItems() {
		List<Role> roles = new RoleDao().findAll();
		SelectItem[] items = new SelectItem[roles.size()];
		int i = 0;
		for (Role role : roles) {
			items[i++] = new SelectItem(role, role.getName());
		}
		return items;
	}

	public SelectItem[] getAllVcsSelectItems() {
		SelectItem[] items = new SelectItem[Vcs.values().length];
		int i = 0;
		for (Vcs vcs : Vcs.values()) {
			items[i++] = new SelectItem(vcs, vcs.name());
		}
		return items;
	}

}
