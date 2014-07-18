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
package de.egore911.versioning.persistence.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.joda.time.LocalDateTime;

import de.egore911.versioning.persistence.model.Deployment;
import de.egore911.versioning.persistence.model.Project;
import de.egore911.versioning.persistence.model.Server;
import de.egore911.versioning.persistence.selector.DeploymentSelector;
import de.egore911.versioning.util.EntityManagerUtil;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class DeploymentDao extends AbstractDao<Deployment> {

	@Override
	protected Class<Deployment> getEntityClass() {
		return Deployment.class;
	}

	@Override
	protected DeploymentSelector createSelector() {
		return new DeploymentSelector();
	}

	public List<Deployment> getCurrentDeployments(Server server) {
		DeploymentSelector deploymentSelector = createSelector();
		deploymentSelector.setDeployedOn(server);
		deploymentSelector.setUneployed(Boolean.FALSE);
		return deploymentSelector.findAll();
	}

	public List<Deployment> getAllDeployments(Server server, LocalDateTime maxAge) {
		DeploymentSelector deploymentSelector = createSelector();
		deploymentSelector.setDeployedOn(server);
		deploymentSelector.withDeployedAfter(maxAge);
		deploymentSelector.withUndeployedAfter(maxAge);
		return deploymentSelector.findAll();
	}

	public Deployment getCurrentDeployment(Server server, Project project) {
		DeploymentSelector deploymentSelector = createSelector();
		deploymentSelector.setDeployedOn(server);
		deploymentSelector.setProject(project);
		deploymentSelector.setUneployed(Boolean.FALSE);
		return deploymentSelector.find();
	}

	public void replace(Deployment currentDeployment, Deployment newDeployment) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		boolean ourOwnTransaction = !em.getTransaction().isActive();
		if (ourOwnTransaction) {
			em.getTransaction().begin();
		}
		try {
			if (currentDeployment != null) {
				currentDeployment.setUndeployment(LocalDateTime.now());
				em.merge(currentDeployment);
			}
			em.merge(newDeployment);
			if (ourOwnTransaction) {
				em.getTransaction().commit();
			}
		} finally {
			if (ourOwnTransaction && em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
	}

}
