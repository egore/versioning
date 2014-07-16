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
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.joda.time.LocalDateTime;

import de.egore911.versioning.persistence.model.Deployment;
import de.egore911.versioning.persistence.model.Deployment_;
import de.egore911.versioning.persistence.model.Project;
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

	@Override
	protected Class<Deployment> getEntityClass() {
		return Deployment.class;
	}

	@Override
	protected List<Predicate> generatePredicateList(CriteriaBuilder builder,
			Root<Deployment> from) {
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

	public DeploymentSelector withUndeployedAfter(LocalDateTime undeployedAfter) {
		this.undeployedAfter = undeployedAfter;
		return this;
	}

	public DeploymentSelector withDeployedAfter(LocalDateTime deployedAfter) {
		this.deployedAfter = deployedAfter;
		return this;
	}
}
