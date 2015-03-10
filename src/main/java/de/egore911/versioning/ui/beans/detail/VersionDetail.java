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

import de.egore911.versioning.persistence.dao.VersionDao;
import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.Version;
import de.egore911.versioning.util.SessionUtil;
import de.egore911.versioning.util.security.RequiresPermission;
import de.egore911.versioning.util.vcs.Provider;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@ManagedBean(name = "versionDetail")
@RequestScoped
@RequiresPermission(Permission.CREATE_VERSIONS)
public class VersionDetail extends AbstractDetail<Version> {

	@Override
	protected VersionDao getDao() {
		return new VersionDao();
	}

	@Override
	protected Version createEmpty() {
		return new Version();
	}

	public String save() {

		if (!validate("version")) {
			return "";
		}

		Version version = getInstance();

		Provider provider = version.getProject().getProvider();
		if (!provider.tagExists(version.getProject(), version.getVcsTag())) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ResourceBundle bundle = SessionUtil.getBundle();
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					bundle.getString("tag_not_found"),
					bundle.getString("tag_not_found_detail"));
			facesContext.addMessage("main:user_password", message);
			return "";
		}

		getDao().save(version);
		setInstance(null);
		return "/versions.xhtml";
	}

}
