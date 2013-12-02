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

import de.egore911.versioning.persistence.model.IntegerDbObject;
import de.egore911.versioning.persistence.selector.AbstractSelector;
import de.egore911.versioning.persistence.selector.DefaultSelector;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class DefaultDao<T extends IntegerDbObject> extends AbstractDao<T> {

	private Class<T> klass;

	public DefaultDao(Class<T> klass) {
		this.klass = klass;
	}

	@Override
	protected Class<T> getEntityClass() {
		return klass;
	}

	@Override
	protected AbstractSelector<T> createSelector() {
		return new DefaultSelector<>(klass);
	}

}
