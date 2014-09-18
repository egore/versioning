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

import org.apache.commons.lang3.StringUtils;
import org.apache.deltaspike.core.api.provider.BeanProvider;

import de.egore911.versioning.persistence.dao.VcshostDao;
import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.Project;
import de.egore911.versioning.persistence.model.VcsHost;
import de.egore911.versioning.util.SessionUtil;
import de.egore911.versioning.util.security.RequiresPermission;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@Named
@RequiresPermission(Permission.ADMIN_SETTINGS)
public class VcshostDetail extends AbstractDetail<VcsHost> {

	private static final long serialVersionUID = -6183158793589999497L;

	private String password;
	private String passwordVerify;

	@Override
	protected VcshostDao getDao() {
		return BeanProvider.getContextualReference(VcshostDao.class);
	}

	@Override
	protected VcsHost createEmpty() {
		return new VcsHost();
	}

	public String save() {

		if (!validate("vcshost")) {
			return "";
		}

		if (StringUtils.isNotEmpty(password)
				|| StringUtils.isNotEmpty(passwordVerify)) {
			if (password != null && !password.equals(passwordVerify)) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				ResourceBundle bundle = SessionUtil.getBundle();
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						bundle.getString("passwords_dont_match"),
						bundle.getString("passwords_dont_match_detail"));
				facesContext.addMessage("main:vcshost_password", message);
				password = null;
				passwordVerify = null;
				return "";
			} else {
				getInstance().setPassword(password);
			}
		}
		getDao().save(getInstance());
		setInstance(null);
		return "/vcshosts.xhtml";
	}

	public void delete() {
		if (!getInstance().getProjects().isEmpty()) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ResourceBundle bundle = SessionUtil.getBundle();
			StringBuilder projectNames = new StringBuilder();
			for (Project project : getInstance().getProjects()) {
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

		getDao().remove(getInstance());
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordVerify() {
		return passwordVerify;
	}

	public void setPasswordVerify(String passwordVerify) {
		this.passwordVerify = passwordVerify;
	}

}
