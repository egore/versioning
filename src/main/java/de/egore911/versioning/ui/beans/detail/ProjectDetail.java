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

import de.egore911.versioning.persistence.dao.MavenRepositoryDao;
import de.egore911.versioning.persistence.dao.ProjectDao;
import de.egore911.versioning.persistence.dao.ServerDao;
import de.egore911.versioning.persistence.dao.TagTransformerDao;
import de.egore911.versioning.persistence.dao.VcshostDao;
import de.egore911.versioning.persistence.model.MavenArtifact;
import de.egore911.versioning.persistence.model.MavenRepository;
import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.Project;
import de.egore911.versioning.persistence.model.Server;
import de.egore911.versioning.persistence.model.SpacerUrl;
import de.egore911.versioning.persistence.model.TagTransformer;
import de.egore911.versioning.persistence.model.VcsHost;
import de.egore911.versioning.persistence.model.War;
import de.egore911.versioning.util.security.RequiresPermission;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@ManagedBean(name = "projectDetail")
@RequestScoped
@RequiresPermission(Permission.ADMIN_SETTINGS)
public class ProjectDetail extends AbstractDetail<Project> {

	@Override
	protected ProjectDao getDao() {
		return new ProjectDao();
	}

	@Override
	protected Project createEmpty() {
		return new Project();
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

	public String chooseWar() {
		Project project = getInstance();
		War war = new War();
		project.setWar(war);
		war.setProject(project);
		setInstance(project);
		return "";
	}

	public String chooseMavenArtifact() {
		War war = getInstance().getWar();
		MavenArtifact mavenArtifact = new MavenArtifact();
		war.setMavenArtifact(mavenArtifact);
		mavenArtifact.setWar(war);
		setInstance(getInstance());
		return "";
	}

	public String chooseSpacerUrl() {
		War war = getInstance().getWar();
		SpacerUrl spacerUrl = new SpacerUrl();
		war.setSpacerUrl(spacerUrl);
		spacerUrl.setWar(war);
		setInstance(getInstance());
		return "";
	}

	public String save() {
		getDao().save(getInstance());
		setInstance(null);
		return "/projects.xhtml";
	}

}
