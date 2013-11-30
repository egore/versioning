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

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;

import de.egore911.versioning.persistence.dao.UserDao;
import de.egore911.versioning.persistence.model.User;
import de.egore911.versioning.util.SessionUtil;
import de.egore911.versioning.util.UserUtil;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@ManagedBean(name = "loginBean")
public class LoginBean {

	private final SessionUtil sessionUtil = new SessionUtil();

	private String login;
	private String password;

	public String performLogin() {

		// No login given, abort
		if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password)) {
			return "";
		}

		// Check if the user exists
		UserDao userDao = new UserDao();
		User user = userDao.getUser(login,
				new UserUtil().hashPassword(password));
		if (user != null) {
			// Found our user, show the versions
			sessionUtil.setLoggedInUser(user);
			// XXX: adapt if the user actually has USE permission
			return "/versions.xhtml";
		} else {
			// Invalid login
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ResourceBundle bundle = sessionUtil.getBundle();
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					bundle.getString("invalid_credentials"),
					bundle.getString("invalid_credentials_detail"));
			facesContext.addMessage("main:user_login", message);

			// Clear login data
			login = null;
			password = null;

			return "";
		}
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}