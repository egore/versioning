/*
 * Copyright 2014  Christoph Brill <egore911@gmail.com>
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

import de.egore911.versioning.persistence.model.User;
import de.egore911.versioning.ui.beans.detail.UserDetail;
import de.egore911.versioning.util.SessionUtil;
import de.egore911.versioning.util.UserUtil;
import de.egore911.versioning.util.security.RequiresPermission;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@ManagedBean(name = "profileBean")
@RequestScoped
@RequiresPermission
public class ProfileBean extends UserDetail {

	@Override
	protected User createEmpty() {
		return reattachUser(SessionUtil.getLoggedInUser());
	}

	@Override
	protected User load() {
		return reattachUser(SessionUtil.getLoggedInUser());
	}

	@Override
	public String save() {
		super.save();
		return UserUtil.getStartpage(getInstance());
	}

}
