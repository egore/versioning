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

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import de.egore911.versioning.persistence.dao.MavenRepositoryDao;
import de.egore911.versioning.persistence.model.MavenRepository;
import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.ui.logic.MavenrepositoryUtil;
import de.egore911.versioning.util.security.RequiresPermission;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@ManagedBean(name = "mavenrepositoryDetail")
@RequestScoped
@RequiresPermission(Permission.ADMIN_SETTINGS)
public class MavenrepositoryDetail extends AbstractDetail<MavenRepository> {

	@Override
	protected MavenRepositoryDao getDao() {
		return new MavenRepositoryDao();
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
		if (MavenrepositoryUtil.isDeletable(instance)) {
			getDao().remove(instance);
		}
	}

}
