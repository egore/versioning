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

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

import de.egore911.versioning.persistence.dao.VcshostDao;
import de.egore911.versioning.persistence.model.Vcs;
import de.egore911.versioning.persistence.model.VcsHost;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@ManagedBean(name = "vcshostDetail")
@RequestScoped
public class VcshostDetail extends AbstractDetail<VcsHost> {

	@Override
	public VcsHost getInstance() {
		if (instance == null) {
			instance = new VcsHost();
		}
		return instance;
	}

	@Override
	protected VcshostDao getDao() {
		return new VcshostDao();
	}

	public SelectItem[] getVcsSelectItems() {
		SelectItem[] items = new SelectItem[Vcs.values().length];
		int i = 0;
		for (Vcs vcs : Vcs.values()) {
			items[i++] = new SelectItem(vcs, vcs.name());
		}
		return items;
	}

	public String save() {
		getDao().save(getInstance());
		return "/vcshosts.xhtml";
	}

}
