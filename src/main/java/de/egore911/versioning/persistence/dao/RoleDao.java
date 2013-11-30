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
package de.egore911.versioning.persistence.dao;

import java.util.List;

import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.Role;
import de.egore911.versioning.persistence.selector.RoleSelector;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class RoleDao extends AbstractDao<Role> {

	@Override
	protected Class<Role> getEntityClass() {
		return Role.class;
	}

	@Override
	protected RoleSelector createSelector() {
		return new RoleSelector();
	}

	public List<Role> withPermission(Permission permission) {
		RoleSelector roleSelector = createSelector();
		roleSelector.setPermission(permission);
		return roleSelector.findAll();
	}

}
