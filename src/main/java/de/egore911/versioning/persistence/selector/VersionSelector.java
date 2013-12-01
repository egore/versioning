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
import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import de.egore911.versioning.persistence.model.Version;
import de.egore911.versioning.persistence.model.Version_;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class VersionSelector extends AbstractSelector<Version> {
	@Override
	protected Class<Version> getEntityClass() {
		return Version.class;
	}

	@Override
	protected List<Predicate> generatePredicateList(CriteriaBuilder builder,
			Root<Version> from) {
		List<Predicate> predicates = new ArrayList<>();
		return predicates;
	}

	@Override
	protected List<Order> generateOrderList(CriteriaBuilder builder,
			Root<Version> from) {
		return Arrays.asList(builder.asc(from.get(Version_.project)),
				builder.asc(from.get(Version_.vcsTag)));
	}

}
