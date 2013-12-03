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

import org.apache.deltaspike.core.api.provider.BeanProvider;

import de.egore911.versioning.persistence.model.User;
import de.egore911.versioning.persistence.selector.UserSelector;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class UserDao extends AbstractDao<User> {

	private static final long serialVersionUID = 3737442309594910399L;

	@Override
	protected Class<User> getEntityClass() {
		return User.class;
	}

	@Override
	protected UserSelector createSelector() {
		return BeanProvider.getContextualReference(UserSelector.class);
	}

	public User getUser(String login, String passwordHash) {
		UserSelector userSelector = createSelector();
		userSelector.setLogin(login);
		userSelector.setPasswordHash(passwordHash);
		return userSelector.find();
	}

}
