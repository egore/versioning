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

import javax.faces.bean.ManagedProperty;
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

	@ManagedProperty(value = "#{param.id}")
	private Integer id;

	protected T instance;

	public T getInstance() {
		// No request cache yet, determine it
		if (instance == null) {
			HttpSession session = new SessionUtil().getSession();
			instance = (T) session.getAttribute(this.getClass().getSimpleName()
					+ "_instance");
			if (instance == null) {
				if (id != null) {
					instance = getDao().findById(id);
				}
				if (instance == null) {
					instance = createEmpty();
				}
			}
			session.setAttribute(this.getClass().getSimpleName() + "_instance",
					instance);
		}
		return instance;
	}

	public void setInstance(T instance) {
		HttpSession session = new SessionUtil().getSession();
		session.setAttribute(this.getClass().getSimpleName() + "_instance",
				instance);
		this.instance = instance;
	}

	public boolean isManaged() {
		return getInstance() != null && getInstance().getId() != null;
	}

	protected abstract AbstractDao<T> getDao();

	protected abstract T createEmpty();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
		// If a ID got passed we were called with a parameter in the URL
		if (id != null) {
			setInstance(null);
		}
	}

}
