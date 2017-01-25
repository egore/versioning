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
package de.egore911.versioning.persistence.dao;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import de.egore911.persistence.dao.AbstractDao;
import de.egore911.persistence.util.EntityManagerUtil;
import de.egore911.versioning.persistence.model.DeploymentEntity;
import de.egore911.versioning.persistence.model.ProjectEntity;
import de.egore911.versioning.persistence.model.ServerEntity;
import de.egore911.versioning.persistence.selector.DeploymentSelector;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class DeploymentDao extends AbstractDao<DeploymentEntity> {

	@Override
	protected Class<DeploymentEntity> getEntityClass() {
		return DeploymentEntity.class;
	}

	@Override
	protected DeploymentSelector createSelector() {
		return new DeploymentSelector();
	}

	public List<DeploymentEntity> getCurrentDeployments(ServerEntity server) {
		DeploymentSelector deploymentSelector = createSelector();
		deploymentSelector.setDeployedOn(server);
		deploymentSelector.setUneployed(Boolean.FALSE);
		deploymentSelector.setExcludeDeleted(Boolean.TRUE);
		return deploymentSelector.findAll();
	}

	public List<DeploymentEntity> getAllDeployments(ServerEntity server, LocalDateTime maxAge) {
		DeploymentSelector deploymentSelector = createSelector();
		deploymentSelector.setDeployedOn(server);
		deploymentSelector.withDeployedAfter(maxAge);
		deploymentSelector.withUndeployedAfter(maxAge);
		return deploymentSelector.findAll();
	}

	public DeploymentEntity getCurrentDeployment(ServerEntity server, ProjectEntity project) {
		DeploymentSelector deploymentSelector = createSelector();
		deploymentSelector.setDeployedOn(server);
		deploymentSelector.setProject(project);
		deploymentSelector.setUneployed(Boolean.FALSE);
		return deploymentSelector.find();
	}

	public void replace(DeploymentEntity currentDeployment, DeploymentEntity newDeployment) {
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

	public List<DeploymentEntity> findByIds(List<Integer> ids) {
		return createSelector().withIds(ids).findAll();
	}

}
