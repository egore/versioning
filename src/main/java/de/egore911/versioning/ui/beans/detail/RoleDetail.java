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

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

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

	public SelectItem[] getAllPermissionSelectItems() {
		SelectItem[] items = new SelectItem[Permission.values().length];
		int i = 0;
		for (Permission permission : Permission.values()) {
			items[i++] = new SelectItem(permission, permission.name());
		}
		return items;
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
