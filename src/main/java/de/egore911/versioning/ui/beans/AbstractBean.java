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

import javax.annotation.PostConstruct;

import org.hibernate.Hibernate;

import de.egore911.versioning.persistence.dao.UserDao;
import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.Role;
import de.egore911.versioning.persistence.model.User;
import de.egore911.versioning.util.SessionUtil;
import de.egore911.versioning.util.security.PermissionException;
import de.egore911.versioning.util.security.RequiresPermission;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class AbstractBean {

	@PostConstruct
	public void postConstruct() {
		RequiresPermission requiresPermission = this.getClass().getAnnotation(
				RequiresPermission.class);
		if (requiresPermission != null) {
			User user = SessionUtil.getLoggedInUser();
			if (user == null) {
				throw new PermissionException();
			}
			user = new UserDao().reattach(user);
			for (Role role : user.getRoles()) {
				Hibernate.initialize(role.getPermissions());
			}
			SessionUtil.setLoggedInUser(user);
			for (Permission permission : requiresPermission.value()) {
				if (user.hasPermission(permission)) {
					return;
				}
			}
			throw new PermissionException(requiresPermission.value());
		}
	}

}
