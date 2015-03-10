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

import de.egore911.versioning.persistence.dao.RoleDao;
import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.Role;
import de.egore911.versioning.util.security.RequiresPermission;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@ManagedBean(name = "roleDetail")
@RequestScoped
@RequiresPermission(Permission.ADMIN_USERS)
public class RoleDetail extends AbstractDetail<Role> {

	@Override
	protected RoleDao getDao() {
		return new RoleDao();
	}

	@Override
	protected Role createEmpty() {
		return new Role();
	}

	public String save() {

		if (!validate("role")) {
			return "";
		}

		getDao().save(getInstance());
		setInstance(null);
		return "/roles.xhtml";
	}

}
