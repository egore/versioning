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

import de.egore911.versioning.persistence.model.Version;
import de.egore911.versioning.persistence.selector.VersionSelector;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class VersionDao extends AbstractDao<Version> {

	private static final long serialVersionUID = 8401614495738118941L;

	@Override
	protected Class<Version> getEntityClass() {
		return Version.class;
	}

	@Override
	protected VersionSelector createSelector() {
		return BeanProvider.getContextualReference(VersionSelector.class);
	}

}
