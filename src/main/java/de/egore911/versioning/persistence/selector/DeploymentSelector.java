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
import java.util.List;

import javax.annotation.Nonnull;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.joda.time.LocalDateTime;

import de.egore911.persistence.selector.AbstractSelector;
import de.egore911.versioning.persistence.model.Deployment;
import de.egore911.versioning.persistence.model.Deployment_;
import de.egore911.versioning.persistence.model.Project;
import de.egore911.versioning.persistence.model.Project_;
import de.egore911.versioning.persistence.model.Server;
import de.egore911.versioning.persistence.model.Version;
import de.egore911.versioning.persistence.model.Version_;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class DeploymentSelector extends AbstractSelector<Deployment> {

	private static final long serialVersionUID = 84030583640758463L;

	private Server deployedOn;
	private LocalDateTime deployedAfter;
	private LocalDateTime undeployedAfter;
	private Boolean isUneployed;
	private Project project;
	private Boolean excludeDeleted;

	@Override
	protected Class<Deployment> getEntityClass() {
		return Deployment.class;
	}

	@Override
	protected List<Predicate> generatePredicateList(CriteriaBuilder builder,
			Root<Deployment> from,
			@Nonnull CriteriaQuery<?> criteriaQuery) {
		List<Predicate> predicates = new ArrayList<>();

		if (deployedOn != null) {
			predicates.add(builder.equal(from.get(Deployment_.server),
					deployedOn));
		}

		if (deployedAfter != null) {
			predicates.add(builder.or(
					builder.isNull(from.get(Deployment_.deployment)),
					builder.greaterThanOrEqualTo(
							from.get(Deployment_.deployment), deployedAfter)));
		}

		if (undeployedAfter != null) {
			predicates.add(builder.or(builder.isNull(from
					.get(Deployment_.undeployment)), builder
					.greaterThanOrEqualTo(from.get(Deployment_.undeployment),
							undeployedAfter)));
		}

		if (isUneployed != null) {
			if (isUneployed) {
				predicates.add(builder.isNotNull(from
						.get(Deployment_.undeployment)));
			} else {
				predicates.add(builder.isNull(from
						.get(Deployment_.undeployment)));
			}
		}

		if (Boolean.TRUE.equals(excludeDeleted)) {
			Join<Deployment, Version> fromVersion = from
					.join(Deployment_.version);
			Join<Version,Project> fromProject = fromVersion
					.join(Version_.project);
			predicates.add(builder.equal(fromProject.get(Project_.deleted),
					Boolean.FALSE));
		}

		if (project != null) {
			Join<Deployment, Version> fromVersion = from
					.join(Deployment_.version);
			predicates.add(builder.equal(fromVersion.get(Version_.project),
					project));
		}

		return predicates;
	}

	public Server getDeployedOn() {
		return deployedOn;
	}

	public void setDeployedOn(Server deployedOn) {
		this.deployedOn = deployedOn;
	}

	public Boolean getUneployed() {
		return isUneployed;
	}

	public void setUneployed(Boolean uneployed) {
		isUneployed = uneployed;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Boolean getExcludeDeleted() {
		return excludeDeleted;
	}

	public void setExcludeDeleted(Boolean excludeDeleted) {
		this.excludeDeleted = excludeDeleted;
	}

	public DeploymentSelector withUndeployedAfter(LocalDateTime undeployedAfter) {
		this.undeployedAfter = undeployedAfter;
		return this;
	}

	public DeploymentSelector withDeployedAfter(LocalDateTime deployedAfter) {
		this.deployedAfter = deployedAfter;
		return this;
	}
}
