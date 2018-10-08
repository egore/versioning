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

import de.egore911.appframework.persistence.selector.AbstractResourceSelector;
import de.egore911.versioning.persistence.model.DeploymentEntity;
import de.egore911.versioning.persistence.model.DeploymentEntity_;
import de.egore911.versioning.persistence.model.ProjectEntity;
import de.egore911.versioning.persistence.model.ProjectEntity_;
import de.egore911.versioning.persistence.model.ServerEntity;
import de.egore911.versioning.persistence.model.ServerEntity_;
import de.egore911.versioning.persistence.model.VersionEntity;
import de.egore911.versioning.persistence.model.VersionEntity_;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class DeploymentSelector
		extends AbstractResourceSelector<DeploymentEntity> {

	private ServerEntity deployedOn;
	private LocalDateTime deployedAfter;
	private LocalDateTime undeployedAfter;
	private Boolean isUneployed;
	private ProjectEntity project;
	private Boolean excludeDeleted;
	private String search;

	@Nonnull
	@Override
	protected Class<DeploymentEntity> getEntityClass() {
		return DeploymentEntity.class;
	}

	@Nonnull
	@Override
	protected List<Predicate> generatePredicateList(
			@Nonnull CriteriaBuilder builder,
			@Nonnull Root<DeploymentEntity> from,
			@Nonnull CriteriaQuery<?> criteriaQuery) {
		List<Predicate> predicates = super.generatePredicateList(builder, from,
				criteriaQuery);

		if (deployedOn != null) {
			predicates.add(builder.equal(from.get(DeploymentEntity_.server),
					deployedOn));
		}

		if (deployedAfter != null) {
			predicates.add(builder.or(
					builder.isNull(from.get(DeploymentEntity_.deployment)),
					builder.greaterThanOrEqualTo(
							from.get(DeploymentEntity_.deployment),
							deployedAfter)));
		}

		if (undeployedAfter != null) {
			predicates.add(builder.or(
					builder.isNull(from.get(DeploymentEntity_.undeployment)),
					builder.greaterThanOrEqualTo(
							from.get(DeploymentEntity_.undeployment),
							undeployedAfter)));
		}

		if (isUneployed != null) {
			if (isUneployed) {
				predicates.add(builder
						.isNotNull(from.get(DeploymentEntity_.undeployment)));
			} else {
				predicates.add(builder
						.isNull(from.get(DeploymentEntity_.undeployment)));
			}
		}

		if (Boolean.TRUE.equals(excludeDeleted)) {
			Join<DeploymentEntity, VersionEntity> fromVersion = from
					.join(DeploymentEntity_.version);
			Join<VersionEntity, ProjectEntity> fromProject = fromVersion
					.join(VersionEntity_.project);
			predicates.add(builder.equal(
					fromProject.get(ProjectEntity_.deleted), Boolean.FALSE));
		}

		if (project != null) {
			Join<DeploymentEntity, VersionEntity> fromVersion = from
					.join(DeploymentEntity_.version);
			predicates.add(builder
					.equal(fromVersion.get(VersionEntity_.project), project));
		}

		if (StringUtils.isNotEmpty(search)) {
			String likePattern = '%' + search + '%';
			predicates
					.add(builder.or(
							builder.like(
									from.get(DeploymentEntity_.version).get(
											VersionEntity_.vcsTag),
									likePattern),
							builder.like(from.get(DeploymentEntity_.version)
									.get(VersionEntity_.project).get(
											ProjectEntity_.name),
									likePattern),
							builder.like(from.get(DeploymentEntity_.server)
									.get(ServerEntity_.name), likePattern)));
		}

		return predicates;
	}

	public DeploymentSelector withDeployedOn(ServerEntity deployedOn) {
		this.deployedOn = deployedOn;
		return this;
	}

	public DeploymentSelector withUneployed(Boolean uneployed) {
		isUneployed = uneployed;
		return this;
	}

	public DeploymentSelector withProject(ProjectEntity project) {
		this.project = project;
		return this;
	}

	public DeploymentSelector withExcludeDeleted(Boolean excludeDeleted) {
		this.excludeDeleted = excludeDeleted;
		return this;
	}

	public DeploymentSelector withUndeployedAfter(
			LocalDateTime undeployedAfter) {
		this.undeployedAfter = undeployedAfter;
		return this;
	}

	public DeploymentSelector withDeployedAfter(LocalDateTime deployedAfter) {
		this.deployedAfter = deployedAfter;
		return this;
	}

	@Override
	public DeploymentSelector withSearch(String search) {
		this.search = search;
		return this;
	}

}
