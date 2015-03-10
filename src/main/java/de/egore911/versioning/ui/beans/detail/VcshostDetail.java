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

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;

import de.egore911.versioning.persistence.dao.VcshostDao;
import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.Project;
import de.egore911.versioning.persistence.model.VcsHost;
import de.egore911.versioning.util.SessionUtil;
import de.egore911.versioning.util.security.RequiresPermission;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@ManagedBean(name = "vcshostDetail")
@RequestScoped
@RequiresPermission(Permission.ADMIN_SETTINGS)
public class VcshostDetail extends AbstractDetail<VcsHost> {

	private String password;
	private String passwordVerify;

	@Override
	protected VcshostDao getDao() {
		return new VcshostDao();
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
		if (!instance.getProjects().isEmpty()) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ResourceBundle bundle = SessionUtil.getBundle();
			StringBuilder projectNames = new StringBuilder();
			for (Project project : instance.getProjects()) {
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

		getDao().remove(instance);
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
