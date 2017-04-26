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

import java.util.Arrays;
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
import de.egore911.versioning.persistence.model.ProjectEntity;
import de.egore911.versioning.persistence.model.ProjectEntity_;
import de.egore911.versioning.persistence.model.VersionEntity;
import de.egore911.versioning.persistence.model.VersionEntity_;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class VersionSelector extends AbstractResourceSelector<VersionEntity> {

	private static final long serialVersionUID = -2047907469123340003L;

	private ProjectEntity project;
	private String vcsTag;
	private String projectNameLike;
	private String vcsTagLike;
	private String search;
	
	public VersionSelector() {
		withSortColumn("created");
		withAscending(false);
	}

	@Override
	protected Class<VersionEntity> getEntityClass() {
		return VersionEntity.class;
	}

	@Override
	protected List<Predicate> generatePredicateList(CriteriaBuilder builder,
			Root<VersionEntity> from,
			@Nonnull CriteriaQuery<?> criteriaQuery) {
		List<Predicate> predicates = super.generatePredicateList(builder, from, criteriaQuery);

		if (project != null) {
			predicates.add(builder.equal(from.get(VersionEntity_.project), project));
		}

		if (vcsTag != null) {
			predicates.add(builder.equal(from.get(VersionEntity_.vcsTag), vcsTag));
		}

		if (StringUtils.isNotEmpty(projectNameLike)) {
			predicates.add(builder.like(
					from.get(VersionEntity_.project).get(ProjectEntity_.name), "%"
							+ projectNameLike + "%"));
		}

		if (StringUtils.isNotEmpty(vcsTagLike)) {
			predicates.add(builder.like(from.get(VersionEntity_.vcsTag), "%"
					+ vcsTagLike + "%"));
		}

		if (StringUtils.isNotEmpty(search)) {
			String likePattern = '%' + search + '%';
			predicates.add(
					builder.or(
							builder.like(from.get(VersionEntity_.vcsTag), likePattern),
							builder.like(from.get(VersionEntity_.project).get(ProjectEntity_.name), likePattern)
					)
			);
		}

		return predicates;
	}

	@Override
	protected List<Order> generateOrderList(CriteriaBuilder builder,
			Root<VersionEntity> from) {
		if (StringUtils.isNotEmpty(sortColumn)) {
			if ("project.name".equals(sortColumn)) {
				if (!Boolean.FALSE.equals(ascending)) {
					return Arrays
							.asList(builder.asc(from.get(VersionEntity_.project).get(ProjectEntity_.name)),
									builder.asc(from.get(VersionEntity_.created)));
				}
				return Arrays.asList(
						builder.desc(from.get(VersionEntity_.project).get(ProjectEntity_.name)),
						builder.asc(from.get(VersionEntity_.created)));
			} else {
				if (!Boolean.FALSE.equals(ascending)) {
					return Collections.singletonList(builder.asc(from
							.get(sortColumn)));
				}
				return Collections.singletonList(builder.desc(from
						.get(sortColumn)));
			}
		}
		return getDefaultOrderList(builder, from);
	}

	@Override
	protected List<Order> getDefaultOrderList(CriteriaBuilder builder,
			Root<VersionEntity> from) {
		return Arrays.asList(builder.asc(from.get(VersionEntity_.project)),
				builder.asc(from.get(VersionEntity_.vcsTag)));
	}

	public VersionSelector withProject(ProjectEntity project) {
		this.project = project;
		return this;
	}

	public VersionSelector withVcsTag(String vcsTag) {
		this.vcsTag = vcsTag;
		return this;
	}

	public VersionSelector withProjectNameLike(String projectNameLike) {
		this.projectNameLike = projectNameLike;
		return this;
	}

	public VersionSelector withVcsTagLike(String vcsTagLike) {
		this.vcsTagLike = vcsTagLike;
		return this;
	}

	@Override
	public VersionSelector withSearch(String search) {
		this.search = search;
		return this;
	}

}
