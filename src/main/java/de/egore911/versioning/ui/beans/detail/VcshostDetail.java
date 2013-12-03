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

import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import de.egore911.versioning.persistence.dao.VcshostDao;
import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.Vcs;
import de.egore911.versioning.persistence.model.VcsHost;
import de.egore911.versioning.util.SessionUtil;
import de.egore911.versioning.util.security.RequiresPermission;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@Named("vcshostDetail")
@ConversationScoped
@RequiresPermission(Permission.ADMIN_SETTINGS)
public class VcshostDetail extends AbstractDetail<VcsHost> {

	private static final long serialVersionUID = 4167319926643191753L;

	private final SessionUtil sessionUtil = new SessionUtil();

	@Inject
	private VcshostDao vcshostDao;

	private String password;
	private String passwordVerify;

	@Override
	public VcsHost getInstance() {
		if (instance == null) {
			instance = new VcsHost();
		}
		return instance;
	}

	@Override
	protected VcshostDao getDao() {
		return vcshostDao;
	}

	public SelectItem[] getVcsSelectItems() {
		SelectItem[] items = new SelectItem[Vcs.values().length];
		int i = 0;
		for (Vcs vcs : Vcs.values()) {
			items[i++] = new SelectItem(vcs, vcs.name());
		}
		return items;
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
				facesContext.addMessage("main:vcshost_password", message);
				password = null;
				passwordVerify = null;
				return "";
			} else {
				getInstance().setPassword(password);
			}
		}
		getDao().save(getInstance());
		if (!conversation.isTransient()) {
			conversation.end();
		}
		return "/vcshosts.xhtml";
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
