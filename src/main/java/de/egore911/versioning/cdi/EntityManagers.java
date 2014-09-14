/*
 * Copyright 2013-2014  Christoph Brill <egore911@gmail.com>
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
package de.egore911.versioning.cdi;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.egore911.versioning.util.listener.StartupListener;

public class EntityManagers {

	@PersistenceContext(unitName = "versioning")
	private EntityManager entityManager;

	@Produces
	@RequestScoped
	protected EntityManager createEntityManager() {
		if (this.entityManager == null) {
			return StartupListener.createEntityManager();
		}
		return this.entityManager;
	}

	protected void closeEntityManager(@Disposes EntityManager entityManager) {
		if (entityManager.isOpen()) {
			entityManager.close();
		}
	}

}