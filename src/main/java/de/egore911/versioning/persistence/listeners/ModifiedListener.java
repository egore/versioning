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
package de.egore911.versioning.persistence.listeners;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.joda.time.LocalDateTime;

import de.egore911.versioning.persistence.dao.UserDao;
import de.egore911.versioning.persistence.model.DbObject;
import de.egore911.versioning.persistence.model.User;
import de.egore911.versioning.util.SessionUtil;
import de.egore911.versioning.util.SessionUtil.LoggedInUser;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class ModifiedListener {

	@PrePersist
	public void prePersist(DbObject<?> o) {
		o.setCreated(LocalDateTime.now());
		o.setModified(o.getCreated());

		LoggedInUser loggedInUser = new SessionUtil().getLoggedInUser();
		if (loggedInUser != null) {
			User user = BeanProvider.getContextualReference(UserDao.class)
					.findById(loggedInUser.getId());
			if (user != null) {
				o.setCreatedBy(user);
				o.setModifiedBy(user);
			}
		}
	}

	@PreUpdate
	public void preUpdate(DbObject<?> o) {
		o.setModified(LocalDateTime.now());

		LoggedInUser loggedInUser = new SessionUtil().getLoggedInUser();
		if (loggedInUser != null) {
			User user = BeanProvider.getContextualReference(UserDao.class)
					.findById(loggedInUser.getId());
			if (user != null) {
				o.setModifiedBy(user);
			}
		}
	}

}
