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
package de.egore911.versioning.persistence.selector;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import de.egore911.versioning.util.EntityManagerUtil;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public abstract class AbstractSelector<T> {

	public List<T> findAll() {
		EntityManager em = EntityManagerUtil.getEntityManager();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = builder.createQuery(getEntityClass());
		Root<T> from = cq.from(getEntityClass());
		List<Predicate> predicates = generatePredicateList(builder, from);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		cq.orderBy(generateOrderList(builder, from));
		cq.select(from);
		TypedQuery<T> q = em.createQuery(cq);
		if (limit != null) {
			q.setMaxResults(limit);
		}
		if (offset != null) {
			q.setFirstResult(offset);
		}
		return q.getResultList();
	}

	public T find() {
		EntityManager em = EntityManagerUtil.getEntityManager();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = builder.createQuery(getEntityClass());
		Root<T> from = cq.from(getEntityClass());
		List<Predicate> predicates = generatePredicateList(builder, from);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		cq.select(from);
		TypedQuery<T> q = em.createQuery(cq);
		try {
			return q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public long count() {
		EntityManager em = EntityManagerUtil.getEntityManager();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = builder.createQuery(Long.class);
		Root<T> from = cq.from(getEntityClass());
		List<Predicate> predicates = generatePredicateList(builder, from);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		cq.orderBy(generateOrderList(builder, from));
		cq.select(builder.count(from));
		TypedQuery<Long> q = em.createQuery(cq);
		return q.getSingleResult();
	}

	protected abstract Class<T> getEntityClass();

	protected abstract List<Predicate> generatePredicateList(
			CriteriaBuilder builder, Root<T> from);

	@SuppressWarnings("unused")
	protected List<Order> generateOrderList(CriteriaBuilder builder,
			Root<T> from) {
		return Collections.emptyList();
	}

	private Integer offset;
	private Integer limit;

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

}
