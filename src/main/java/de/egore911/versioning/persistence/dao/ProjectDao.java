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
package de.egore911.versioning.persistence.dao;

import java.util.List;

import org.apache.deltaspike.core.api.provider.BeanProvider;

import de.egore911.versioning.persistence.model.Project;
import de.egore911.versioning.persistence.model.Server;
import de.egore911.versioning.persistence.selector.ProjectSelector;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class ProjectDao extends AbstractDao<Project> {

	private static final long serialVersionUID = 1342586434133194612L;

	@Override
	protected Class<Project> getEntityClass() {
		return Project.class;
	}

	@Override
	protected ProjectSelector createSelector() {
		return BeanProvider.getContextualReference(ProjectSelector.class);
	}

	public List<Project> getConfiguredProjects(Server server) {
		ProjectSelector projectSelector = createSelector();
		projectSelector.setConfiguredForServer(server);
		return projectSelector.findAll();
	}

}
