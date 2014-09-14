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
package de.egore911.versioning.ui.beans.list;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.User;
import de.egore911.versioning.persistence.model.User_;
import de.egore911.versioning.persistence.selector.AbstractSelector;
import de.egore911.versioning.persistence.selector.UserSelector;
import de.egore911.versioning.util.security.RequiresPermission;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@Named
@RequestScoped
@RequiresPermission(Permission.ADMIN_USERS)
public class UserList extends AbstractList<User> {

	@Inject
	@SessionScoped
	private UserSelector selector;

	@Override
	public UserSelector getSelector() {
		return selector;
	}

	@Override
	public void setSelector(AbstractSelector<User> selector) {
		this.selector = (UserSelector) selector;
	}

	@PostConstruct
	public void postConstruct() {
		if (selector.getSortColumn() == null) {
			selector.setSortColumn(User_.name.getName());
			selector.setAscending(Boolean.TRUE);
			selector.setLimit(DEFAULT_LIMIT);
		}
	}

}