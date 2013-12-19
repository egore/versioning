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
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import de.egore911.versioning.persistence.model.BinaryData;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class BinaryDataSelector extends AbstractSelector<BinaryData> {

	private static final long serialVersionUID = 2872006201650134090L;

	@Override
	protected Class<BinaryData> getEntityClass() {
		return BinaryData.class;
	}

	@Override
	protected List<Predicate> generatePredicateList(CriteriaBuilder builder,
			Root<BinaryData> from) {
		List<Predicate> predicates = new ArrayList<>();
		return predicates;
	}

}
