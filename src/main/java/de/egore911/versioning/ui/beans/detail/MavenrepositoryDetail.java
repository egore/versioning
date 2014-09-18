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

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.deltaspike.core.api.provider.BeanProvider;

import de.egore911.versioning.persistence.dao.MavenRepositoryDao;
import de.egore911.versioning.persistence.model.MavenArtifact;
import de.egore911.versioning.persistence.model.MavenRepository;
import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.util.SessionUtil;
import de.egore911.versioning.util.security.RequiresPermission;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@Named
@RequiresPermission(Permission.ADMIN_SETTINGS)
public class MavenrepositoryDetail extends AbstractDetail<MavenRepository> {

	private static final long serialVersionUID = 7344865258148748519L;

	@Override
	protected MavenRepositoryDao getDao() {
		return BeanProvider.getContextualReference(MavenRepositoryDao.class);
	}

	@Override
	protected MavenRepository createEmpty() {
		return new MavenRepository();
	}

	public String save() {

		if (!validate("mavenrepository")) {
			return "";
		}

		getDao().save(getInstance());
		setInstance(null);
		return "/mavenrepositories.xhtml";
	}

	public void delete() {
		if (!getInstance().getMavenArtifacts().isEmpty()) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ResourceBundle bundle = SessionUtil.getBundle();
			StringBuilder projectNames = new StringBuilder();
			for (MavenArtifact mavenArtifact : getInstance().getMavenArtifacts()) {
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

		getDao().remove(getInstance());
	}

}
