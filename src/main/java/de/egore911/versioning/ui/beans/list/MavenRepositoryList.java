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
package de.egore911.versioning.ui.beans.list;

import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import de.egore911.versioning.persistence.dao.MavenRepositoryDao;
import de.egore911.versioning.persistence.model.MavenArtifact;
import de.egore911.versioning.persistence.model.MavenRepository;
import de.egore911.versioning.persistence.model.MavenRepository_;
import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.selector.AbstractSelector;
import de.egore911.versioning.persistence.selector.MavenRepositorySelector;
import de.egore911.versioning.util.SessionUtil;
import de.egore911.versioning.util.security.RequiresPermission;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@Named
@RequestScoped
@RequiresPermission(Permission.ADMIN_SETTINGS)
public class MavenRepositoryList extends AbstractList<MavenRepository> {

	@Inject
	private MavenRepositoryDao mavenRepositoryDao;
	@Inject
	@SessionScoped
	private MavenRepositorySelector selector;

	@Override
	public MavenRepositorySelector getSelector() {
		return selector;
	}

	@Override
	public void setSelector(AbstractSelector<MavenRepository> selector) {
		this.selector = (MavenRepositorySelector) selector;
	}

	@PostConstruct
	public void postConstruct() {
		if (selector.getSortColumn() == null) {
			selector.setSortColumn(MavenRepository_.name.getName());
			selector.setAscending(Boolean.TRUE);
			selector.setLimit(DEFAULT_LIMIT);
		}
	}

	public void delete(Integer id) {
		MavenRepository mavenRepository = mavenRepositoryDao.findById(id);
		if (!mavenRepository.getMavenArtifacts().isEmpty()) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ResourceBundle bundle = SessionUtil.getBundle();
			StringBuilder projectNames = new StringBuilder();
			for (MavenArtifact mavenArtifact : mavenRepository
					.getMavenArtifacts()) {
				if (projectNames.length() > 0) {
					projectNames.append(", ");
				}
				projectNames.append(mavenArtifact.getGroupId());
				projectNames.append(':');
				projectNames.append(mavenArtifact.getArtifactId());
			}
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					bundle.getString("mavenrepository_delete_not_possible_used_by_artifacts"),
					projectNames.toString());
			facesContext.addMessage("main:table", message);
			return;
		}

		mavenRepositoryDao.remove(mavenRepository);
	}

}