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

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.deltaspike.core.api.provider.BeanProvider;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public abstract class AbstractSelector<T> implements Serializable {

	private static final long serialVersionUID = 3479024193093886962L;

	@Nonnull
	public List<T> findAll() {
		EntityManager em = BeanProvider.getContextualReference(EntityManager.class);
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

	@Nullable
	public T find() {
		EntityManager em = BeanProvider.getContextualReference(EntityManager.class);
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
			return (T)null;
		}
	}

	public long count() {
		EntityManager em = BeanProvider.getContextualReference(EntityManager.class);
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = builder.createQuery(Long.class);
		Root<T> from = cq.from(getEntityClass());
		List<Predicate> predicates = generatePredicateList(builder, from);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		cq.select(builder.count(from));
		TypedQuery<Long> q = em.createQuery(cq);
		return q.getSingleResult();
	}

	protected abstract Class<T> getEntityClass();

	protected abstract List<Predicate> generatePredicateList(
			CriteriaBuilder builder, Root<T> from);

	protected List<Order> generateOrderList(CriteriaBuilder builder, Root<T> from) {
		if (StringUtils.isNotEmpty(getSortColumn())) {
			if (!Boolean.FALSE.equals(getAscending())) {
				return Collections.singletonList(builder.asc(from
						.get(getSortColumn())));
			}
			return Collections.singletonList(builder.desc(from
					.get(getSortColumn())));
		}
		return getDefaultOrderList(builder, from);
	}

	@SuppressWarnings("unused")
	protected List<Order> getDefaultOrderList(CriteriaBuilder builder,
			Root<T> from) {
		return Collections.emptyList();
	}

	private Integer offset;
	private Integer limit;
	private String sortColumn;
	private Boolean ascending;

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

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public Boolean getAscending() {
		return ascending;
	}

	public void setAscending(Boolean ascending) {
		this.ascending = ascending;
	}

}
