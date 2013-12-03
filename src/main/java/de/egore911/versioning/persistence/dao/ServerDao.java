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

import de.egore911.versioning.persistence.model.Server;
import de.egore911.versioning.persistence.selector.ServerSelector;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class ServerDao extends AbstractDao<Server> {

	private static final long serialVersionUID = -2215430060514154050L;

	@Override
	protected Class<Server> getEntityClass() {
		return Server.class;
	}

	@Override
	protected ServerSelector createSelector() {
		return BeanProvider.getContextualReference(ServerSelector.class);
	}

	public Server findByName(String name) {
		ServerSelector serverSelector = createSelector();
		serverSelector.setName(name);
		return serverSelector.find();
	}

}
