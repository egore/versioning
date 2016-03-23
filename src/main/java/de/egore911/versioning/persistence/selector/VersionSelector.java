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

import de.egore911.persistence.selector.AbstractSelector;
import de.egore911.versioning.persistence.model.Project;
import de.egore911.versioning.persistence.model.Project_;
import de.egore911.versioning.persistence.model.Version;
import de.egore911.versioning.persistence.model.Version_;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class VersionSelector extends AbstractSelector<Version> {

	private static final long serialVersionUID = -2047907469123340003L;

	private Project project;
	private String vcsTag;
	private String projectNameLike;
	private String vcsTagLike;

	@Override
	protected Class<Version> getEntityClass() {
		return Version.class;
	}

	@Override
	protected List<Predicate> generatePredicateList(CriteriaBuilder builder,
			Root<Version> from,
			@Nonnull CriteriaQuery<?> criteriaQuery) {
		List<Predicate> predicates = new ArrayList<>();

		if (project != null) {
			predicates.add(builder.equal(from.get(Version_.project), project));
		}

		if (vcsTag != null) {
			predicates.add(builder.equal(from.get(Version_.vcsTag), vcsTag));
		}

		if (StringUtils.isNotEmpty(projectNameLike)) {
			predicates.add(builder.like(
					from.get(Version_.project).get(Project_.name), "%"
							+ projectNameLike + "%"));
		}

		if (StringUtils.isNotEmpty(vcsTagLike)) {
			predicates.add(builder.like(from.get(Version_.vcsTag), "%"
					+ vcsTagLike + "%"));
		}

		return predicates;
	}

	@Override
	protected List<Order> generateOrderList(CriteriaBuilder builder,
			Root<Version> from) {
		String sortColumn = getSortColumn();
		if (StringUtils.isNotEmpty(sortColumn)) {
			if ("projectname".equals(sortColumn)) {
				if (!Boolean.FALSE.equals(getAscending())) {
					return Arrays
							.asList(builder.asc(from.get(Version_.project).get(
									"name")),
									builder.asc(from.get(Version_.created)));
				}
				return Arrays.asList(
						builder.desc(from.get(Version_.project).get("name")),
						builder.asc(from.get(Version_.created)));
			} else {
				if (!Boolean.FALSE.equals(getAscending())) {
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
			Root<Version> from) {
		return Arrays.asList(builder.asc(from.get(Version_.project)),
				builder.asc(from.get(Version_.vcsTag)));
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getVcsTag() {
		return vcsTag;
	}

	public void setVcsTag(String vcsTag) {
		this.vcsTag = vcsTag;
	}

	public String getProjectNameLike() {
		return projectNameLike;
	}

	public void setProjectNameLike(String projectNameLike) {
		this.projectNameLike = projectNameLike;
	}

	public String getVcsTagLike() {
		return vcsTagLike;
	}

	public void setVcsTagLike(String vcsTagLike) {
		this.vcsTagLike = vcsTagLike;
	}

}
