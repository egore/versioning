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
package de.egore911.versioning.ui.beans.detail;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;

import de.egore911.versioning.persistence.dao.UserDao;
import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.User;
import de.egore911.versioning.util.SessionUtil;
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
				ResourceBundle bundle = SessionUtil.getBundle();
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						bundle.getString("passwords_dont_match"),
						bundle.getString("passwords_dont_match_detail"));
				facesContext.addMessage("main:user_password", message);
				password = null;
				passwordVerify = null;
				return "";
			} else {
				getInstance().setPassword(UserUtil.hashPassword(password));
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
				ResourceBundle bundle = SessionUtil.getBundle();
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
				ResourceBundle bundle = SessionUtil.getBundle();
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
