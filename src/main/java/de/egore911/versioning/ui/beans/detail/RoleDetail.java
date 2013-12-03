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

import javax.enterprise.context.ConversationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import de.egore911.versioning.persistence.dao.RoleDao;
import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.Role;
import de.egore911.versioning.util.security.RequiresPermission;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@Named("roleDetail")
@ConversationScoped
@RequiresPermission(Permission.ADMIN_USERS)
public class RoleDetail extends AbstractDetail<Role> {

	private static final long serialVersionUID = 7342109474623621328L;

	@Inject
	private RoleDao roleDao;

	@Override
	public Role getInstance() {
		if (instance == null) {
			instance = new Role();
		}
		return instance;
	}

	@Override
	protected RoleDao getDao() {
		return roleDao;
	}

	public String save() {
		getDao().save(getInstance());
		if (!conversation.isTransient()) {
			conversation.end();
		}
		return "/roles.xhtml";
	}

	public SelectItem[] getAllPermissionSelectItems() {
		SelectItem[] items = new SelectItem[Permission.values().length];
		int i = 0;
		for (Permission permission : Permission.values()) {
			items[i++] = new SelectItem(permission, permission.name());
		}
		return items;
	}

}
