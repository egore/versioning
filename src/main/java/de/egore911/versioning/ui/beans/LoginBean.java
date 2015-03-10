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

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
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
@RequestScoped
public class LoginBean {

	private String login;
	private String password;

	public String performLogin() {

		// No login given, abort
		if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password)) {
			return "";
		}

		// Check if the user exists
		UserDao userDao = new UserDao();
		User user = userDao.getUser(login, UserUtil.hashPassword(password));
		if (user != null) {
			// Found our user, show the versions
			SessionUtil.setLoggedInUser(user);
			return UserUtil.getStartpage(user);
		} else {
			// Invalid login
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ResourceBundle bundle = SessionUtil.getBundle();
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
