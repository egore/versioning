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
package de.egore911.versioning.persistence.selector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.Role;
import de.egore911.versioning.persistence.model.Role_;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class RoleSelector extends AbstractSelector<Role> {

	private Permission permission;

	@Override
	protected Class<Role> getEntityClass() {
		return Role.class;
	}

	@Override
	protected List<Predicate> generatePredicateList(CriteriaBuilder builder,
			Root<Role> from) {
		List<Predicate> predicates = new ArrayList<>();

		if (permission != null) {
			ListJoin<Role, Permission> fromPermission = from
					.join(Role_.permissions);
			predicates.add(fromPermission.in(permission));
		}

		return predicates;
	}

	@Override
	protected List<Order> generateOrderList(CriteriaBuilder builder,
			Root<Role> from) {
		return Collections.singletonList(builder.asc(from.get(Role_.name)));
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

}
