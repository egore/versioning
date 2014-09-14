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

import de.egore911.versioning.persistence.model.DbObject_;
import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.Version;
import de.egore911.versioning.persistence.selector.AbstractSelector;
import de.egore911.versioning.persistence.selector.VersionSelector;
import de.egore911.versioning.util.security.RequiresPermission;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@Named
@RequestScoped
@RequiresPermission({ Permission.CREATE_VERSIONS, Permission.DEPLOY })
public class VersionList extends AbstractList<Version> {

	@Inject
	@SessionScoped
	private VersionSelector selector;

	@Override
	public VersionSelector getSelector() {
		return selector;
	}

	@Override
	public void setSelector(AbstractSelector<Version> selector) {
		this.selector = (VersionSelector) selector;
	}

	@PostConstruct
	public void postConstruct() {
		if (selector.getSortColumn() == null) {
			selector.setSortColumn(DbObject_.created.getName());
			selector.setAscending(Boolean.FALSE);
			selector.setLimit(DEFAULT_LIMIT);
		}
	}

}
