/*
 * Copyright 2013  Christoph Brill <egore911@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package de.egore911.versioning.persistence.selector;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import de.egore911.appframework.persistence.selector.AbstractResourceSelector;
import de.egore911.versioning.persistence.model.ServerEntity;
import de.egore911.versioning.persistence.model.ServerEntity_;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class ServerSelector extends AbstractResourceSelector<ServerEntity> {

	private static final long serialVersionUID = 4957078336902492971L;

	private String name;
	private String nameLike;
	private String descriptionLike;
	private Integer iconId;
	private String search;

	@Override
	protected Class<ServerEntity> getEntityClass() {
		return ServerEntity.class;
	}

	@Override
	protected List<Predicate> generatePredicateList(CriteriaBuilder builder,
			Root<ServerEntity> from,
			@Nonnull CriteriaQuery<?> criteriaQuery) {
		List<Predicate> predicates = super.generatePredicateList(builder, from, criteriaQuery);

		if (StringUtils.isNotEmpty(name)) {
			predicates.add(builder.equal(from.get(ServerEntity_.name), name));
		}

		if (StringUtils.isNotEmpty(nameLike)) {
			predicates.add(builder.like(from.get(ServerEntity_.name), "%" + nameLike
					+ "%"));
		}

		if (StringUtils.isNotEmpty(descriptionLike)) {
			predicates.add(builder.like(from.get(ServerEntity_.description), "%"
					+ descriptionLike + "%"));
		}
		
		if (iconId != null) {
			predicates.add(builder.equal(from.get(ServerEntity_.iconId), iconId));
		}

		if (StringUtils.isNotEmpty(search)) {
			String likePattern = '%' + search + '%';
			predicates.add(
					builder.or(
							builder.like(from.get(ServerEntity_.name), likePattern),
							builder.like(from.get(ServerEntity_.vcsPath), likePattern)
					)
			);
		}

		return predicates;
	}

	@Override
	protected List<Order> getDefaultOrderList(CriteriaBuilder builder,
			Root<ServerEntity> from) {
		return Collections.singletonList(builder.asc(from.get(ServerEntity_.name)));
	}


	public ServerSelector withName(String name) {
		this.name = name;
		return this;
	}


	public ServerSelector withNameLike(String nameLike) {
		this.nameLike = nameLike;
		return this;
	}

	public ServerSelector withDescriptionLike(String descriptionLike) {
		this.descriptionLike = descriptionLike;
		return this;
	}

	public ServerSelector withIconId(Integer iconId) {
		this.iconId = iconId;
		return this;
	}

	@Override
	public ServerSelector withSearch(String search) {
		this.search = search;
		return this;
	}
}
