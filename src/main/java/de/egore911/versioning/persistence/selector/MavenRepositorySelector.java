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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import de.egore911.versioning.persistence.model.MavenRepository;
import de.egore911.versioning.persistence.model.MavenRepository_;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class MavenRepositorySelector extends AbstractSelector<MavenRepository> {

	private static final long serialVersionUID = 1467355656953767923L;

	private String name;

	@Override
	protected Class<MavenRepository> getEntityClass() {
		return MavenRepository.class;
	}

	@Override
	protected List<Predicate> generatePredicateList(CriteriaBuilder builder,
			Root<MavenRepository> from) {
		List<Predicate> predicates = new ArrayList<>();

		if (StringUtils.isNotEmpty(name)) {
			predicates
					.add(builder.equal(from.get(MavenRepository_.name), name));
		}

		return predicates;
	}

	@Override
	protected List<Order> getDefaultOrderList(CriteriaBuilder builder,
			Root<MavenRepository> from) {
		return Collections.singletonList(builder.asc(from
				.get(MavenRepository_.name)));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
