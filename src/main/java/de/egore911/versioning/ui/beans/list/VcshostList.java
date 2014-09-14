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

import de.egore911.versioning.persistence.dao.VcshostDao;
import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.Project;
import de.egore911.versioning.persistence.model.VcsHost;
import de.egore911.versioning.persistence.model.VcsHost_;
import de.egore911.versioning.persistence.selector.AbstractSelector;
import de.egore911.versioning.persistence.selector.VcshostSelector;
import de.egore911.versioning.util.SessionUtil;
import de.egore911.versioning.util.security.RequiresPermission;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@Named
@RequestScoped
@RequiresPermission(Permission.ADMIN_SETTINGS)
public class VcshostList extends AbstractList<VcsHost> {

	@Inject
	private VcshostDao vcshostDao;

	@Inject
	@SessionScoped
	private VcshostSelector selector;

	@Override
	public VcshostSelector getSelector() {
		return selector;
	}

	@Override
	public void setSelector(AbstractSelector<VcsHost> selector) {
		this.selector = (VcshostSelector) selector;
	}

	@PostConstruct
	public void postConstruct() {
		if (selector.getSortColumn() == null) {
			selector.setSortColumn(VcsHost_.name.getName());
			selector.setAscending(Boolean.TRUE);
			selector.setLimit(DEFAULT_LIMIT);
		}
	}

	public void delete(Integer id) {
		VcsHost vcshost = vcshostDao.findById(id);
		if (!vcshost.getProjects().isEmpty()) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ResourceBundle bundle = SessionUtil.getBundle();
			StringBuilder projectNames = new StringBuilder();
			for (Project project : vcshost.getProjects()) {
				if (projectNames.length() > 0) {
					projectNames.append(", ");
				}
				projectNames.append(project.getName());
			}
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					bundle.getString("vcshost_delete_not_possible_used_by_projects"),
					projectNames.toString());
			facesContext.addMessage("main:table", message);
			return;
		}

		vcshostDao.remove(vcshost);
	}

}
