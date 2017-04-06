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
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import de.egore911.appframework.persistence.selector.AbstractResourceSelector;
import de.egore911.versioning.persistence.model.ProjectEntity;
import de.egore911.versioning.persistence.model.ProjectEntity_;
import de.egore911.versioning.persistence.model.ServerEntity;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class ProjectSelector extends AbstractResourceSelector<ProjectEntity> {

	private static final long serialVersionUID = 6585242967556404330L;

	private ServerEntity configuredForServer;
	private Boolean excludeDeleted;
	private String search;

	@Override
	protected Class<ProjectEntity> getEntityClass() {
		return ProjectEntity.class;
	}

	@Override
	protected List<Predicate> generatePredicateList(CriteriaBuilder builder,
			Root<ProjectEntity> from,
			@Nonnull CriteriaQuery<?> criteriaQuery) {
		List<Predicate> predicates = super.generatePredicateList(builder, from, criteriaQuery);

		if (configuredForServer != null) {
			ListJoin<ProjectEntity, ServerEntity> fromServer = from
					.join(ProjectEntity_.configuredServers);
			predicates.add(fromServer.in(configuredForServer));
		}

		if (Boolean.TRUE.equals(excludeDeleted)) {
			predicates.add(builder.notEqual(from.get(ProjectEntity_.deleted), Boolean.TRUE));
		}

		if (StringUtils.isNotEmpty(search)) {
			String likePattern = '%' + search + '%';
			predicates.add(
					builder.or(
							builder.like(from.get(ProjectEntity_.name), likePattern),
							builder.like(from.get(ProjectEntity_.vcsPath), likePattern)
					)
			);
		}

		return predicates;
	}

	@Override
	protected List<Order> getDefaultOrderList(CriteriaBuilder builder,
			Root<ProjectEntity> from) {
		return Collections.singletonList(builder.asc(from.get(ProjectEntity_.name)));
	}

	public ProjectSelector withConfiguredForServer(ServerEntity configuredForServer) {
		this.configuredForServer = configuredForServer;
		return this;
	}

	public ProjectSelector withExcludeDeleted(Boolean excludeDeleted) {
		this.excludeDeleted = excludeDeleted;
		return this;
	}

	@Override
	public ProjectSelector withSearch(String search) {
		this.search = search;
		return this;
	}
}
