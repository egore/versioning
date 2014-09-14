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

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egore911.versioning.persistence.model.IntegerDbObject;
import de.egore911.versioning.persistence.selector.AbstractSelector;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public abstract class AbstractDao<T extends IntegerDbObject> {

	private static final Logger log = LoggerFactory
			.getLogger(AbstractDao.class);

	@Inject
	protected EntityManager entityManager;

	public List<T> findAll() {
		if (log.isTraceEnabled()) {
			log.trace("Selecting all {}s", getClass().getSimpleName());
		}
		return createSelector().findAll();
	}

	public List<T> findAll(int offset, int limit) {
		if (log.isTraceEnabled()) {
			log.trace("Selecting all {}s from {} to {}", getClass()
					.getSimpleName(), offset, offset + limit);
		}
		AbstractSelector<T> selector = createSelector();
		selector.setOffset(offset);
		selector.setLimit(limit);
		return selector.findAll();
	}

	public List<T> findAll(int offset, int limit, String sortColumn, boolean asc) {
		if (log.isTraceEnabled()) {
			log.trace("Selecting all {}s from {} to {}", getClass()
					.getSimpleName(), offset, offset + limit);
		}
		AbstractSelector<T> selector = createSelector();
		selector.setOffset(offset);
		selector.setLimit(limit);
		selector.setSortColumn(sortColumn);
		selector.setAscending(asc);
		return selector.findAll();
	}

	public T findById(Integer id) {
		if (log.isTraceEnabled()) {
			log.trace("Selecting {}", getClass().getSimpleName());
		}
		return entityManager.find(getEntityClass(), id);
	}

	public long count() {
		if (log.isTraceEnabled()) {
			log.trace("Selecting all {}s", getClass().getSimpleName());
		}
		return createSelector().count();
	}

	@Transactional
	public T save(T entity) {
		return entityManager.merge(entity);
	}

	public T reattach(T entity) {
		if (!entityManager.contains(entity)) {
			entity = entityManager.merge(entity);
		}
		return entity;
	}

	@Transactional
	public void remove(T entity) {
		entityManager.remove(entity);
	}

	protected abstract Class<T> getEntityClass();

	protected abstract AbstractSelector<T> createSelector();

}
