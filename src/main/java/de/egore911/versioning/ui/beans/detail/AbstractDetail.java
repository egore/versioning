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

import javax.servlet.http.HttpSession;

import de.egore911.versioning.persistence.dao.AbstractDao;
import de.egore911.versioning.persistence.model.IntegerDbObject;
import de.egore911.versioning.ui.beans.AbstractBean;
import de.egore911.versioning.util.SessionUtil;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public abstract class AbstractDetail<T extends IntegerDbObject> extends
		AbstractBean {

	protected T instance;

	public abstract T getInstance();

	public void setInstance(T instance) {
		HttpSession session = new SessionUtil().getSession();
		session.setAttribute(this.getClass().getSimpleName() + "_instance",
				instance);
		this.instance = instance;
	}

	public void setId(Integer id) {
		setInstance(getDao().findById(id));
	}

	public Integer getId() {
		if (getInstance() == null) {
			return null;
		}
		return getInstance().getId();
	}

	public boolean isManaged() {
		return getId() != null;
	}

	protected abstract AbstractDao<T> getDao();

}
