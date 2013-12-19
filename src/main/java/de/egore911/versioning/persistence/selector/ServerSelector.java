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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import de.egore911.versioning.persistence.model.Server;
import de.egore911.versioning.persistence.model.Server_;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class ServerSelector extends AbstractSelector<Server> {

	private static final long serialVersionUID = 4957078336902492971L;

	private String name;
	private String nameLike;
	private String descriptionLike;

	@Override
	protected Class<Server> getEntityClass() {
		return Server.class;
	}

	@Override
	protected List<Predicate> generatePredicateList(CriteriaBuilder builder,
			Root<Server> from) {
		List<Predicate> predicates = new ArrayList<>();

		if (StringUtils.isNotEmpty(name)) {
			predicates.add(builder.equal(from.get(Server_.name), name));
		}

		if (StringUtils.isNotEmpty(nameLike)) {
			predicates.add(builder.like(from.get(Server_.name), "%" + nameLike
					+ "%"));
		}

		if (StringUtils.isNotEmpty(descriptionLike)) {
			predicates.add(builder.like(from.get(Server_.description), "%"
					+ descriptionLike + "%"));
		}

		return predicates;
	}

	@Override
	protected List<Order> getDefaultOrderList(CriteriaBuilder builder,
			Root<Server> from) {
		return Collections.singletonList(builder.asc(from.get(Server_.name)));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameLike() {
		return nameLike;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public String getDescriptionLike() {
		return descriptionLike;
	}

	public void setDescriptionLike(String descriptionLike) {
		this.descriptionLike = descriptionLike;
	}

}
