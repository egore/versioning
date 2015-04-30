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
package de.egore911.versioning.ui.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

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
@ManagedBean(name = "selectItemBean")
@RequestScoped
public class SelectItemBean {

	public SelectItem[] getAllProjectSelectItems() {
		List<Project> projects = new ProjectDao().findAllNonDeleted();
		SelectItem[] items = new SelectItem[projects.size()];
		int i = 0;
		for (Project project : projects) {
			items[i++] = new SelectItem(project, project.getName());
		}
		return items;
	}

	public SelectItem[] getAllVcshostSelectItems() {
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

	public SelectItem[] getAllMavenRepositoriesSelectItems() {
		List<MavenRepository> mavenRepositories = new MavenRepositoryDao()
				.findAll();
		SelectItem[] items = new SelectItem[mavenRepositories.size()];
		int i = 0;
		for (MavenRepository mavenRepository : mavenRepositories) {
			items[i++] = new SelectItem(mavenRepository,
					mavenRepository.getName());
		}
		return items;
	}

	public SelectItem[] getAllTagTransformerSelectItems() {
		List<TagTransformer> tagTransformers = new TagTransformerDao()
				.findAll();
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
