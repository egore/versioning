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

import java.text.MessageFormat;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;

import de.egore911.versioning.persistence.dao.RoleDao;
import de.egore911.versioning.persistence.dao.UserDao;
import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.Role;
import de.egore911.versioning.persistence.model.User;
import de.egore911.versioning.util.UserUtil;
import de.egore911.versioning.util.security.RequiresPermission;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@ManagedBean(name = "userDetail")
@RequestScoped
@RequiresPermission(Permission.ADMIN_USERS)
public class UserDetail extends AbstractDetail<User> {

	private String password;
	private String passwordVerify;

	@Override
	protected UserDao getDao() {
		return new UserDao();
	}

	@Override
	protected User createEmpty() {
		return new User();
	}

	public String save() {

		if (StringUtils.isNotEmpty(password)
				|| StringUtils.isNotEmpty(passwordVerify)) {
			if (password != null && !password.equals(passwordVerify)) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				ResourceBundle bundle = sessionUtil.getBundle();
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						bundle.getString("passwords_dont_match"),
						bundle.getString("passwords_dont_match_detail"));
				facesContext.addMessage("main:user_password", message);
				password = null;
				passwordVerify = null;
				return "";
			} else {
				getInstance()
						.setPassword(new UserUtil().hashPassword(password));
			}
		}

		if (!validate("user")) {
			return "";
		}

		User user = new UserDao().findByLogin(getInstance().getLogin());
		if (isManaged()) {
			if (user != null && !getInstance().getId().equals(user.getId())) {
				// Login already taken
				FacesContext facesContext = FacesContext.getCurrentInstance();
				ResourceBundle bundle = sessionUtil.getBundle();
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						bundle.getString("duplicate_login"),
						MessageFormat.format(
								bundle.getString("duplicate_login_detail"),
								getInstance().getLogin()));
				facesContext.addMessage("main:user_login", message);
				getInstance().setLogin(user.getLogin());
				return "";
			}
		} else {
			if (user != null) {
				// Login already taken
				FacesContext facesContext = FacesContext.getCurrentInstance();
				ResourceBundle bundle = sessionUtil.getBundle();
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						bundle.getString("duplicate_login"),
						MessageFormat.format(
								bundle.getString("duplicate_login_detail"),
								getInstance().getLogin()));
				facesContext.addMessage("main:user_login", message);
				getInstance().setLogin(null);
				return "";
			}
		}

		getDao().save(getInstance());
		setInstance(null);
		return "/users.xhtml";
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
