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
package de.egore911.versioning.util;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class to hold the current entity manager for the duration of a
 * request. Done using a {@link ThreadLocal}.
 * 
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class EntityManagerUtil {

	private static final Logger log = LoggerFactory
			.getLogger(EntityManagerUtil.class);

	private static ThreadLocal<EntityManager> entityManagerHolder = new ThreadLocal<>();

	/**
	 * @return the current entity manager for the request
	 * @see #setEntityManager(EntityManager)
	 */
	public static EntityManager getEntityManager() {
		return entityManagerHolder.get();
	}

	/**
	 * @param entityManager
	 *            the current entity manager for the request
	 * @see #getEntityManager()
	 */
	public static void setEntityManager(EntityManager entityManager) {
		if (entityManagerHolder.get() != null) {
			log.error("Replacing existing EntityManger");
		}
		entityManagerHolder.set(entityManager);
	}

	/**
	 * Remove the current entitymanager from the request.
	 */
	public static void clearEntityManager() {
		entityManagerHolder.set(null);
	}
}
