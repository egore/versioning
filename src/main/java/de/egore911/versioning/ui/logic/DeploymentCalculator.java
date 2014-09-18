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
package de.egore911.versioning.ui.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.deltaspike.core.api.provider.BeanProvider;

import de.egore911.versioning.persistence.dao.DeploymentDao;
import de.egore911.versioning.persistence.dao.ProjectDao;
import de.egore911.versioning.persistence.model.Deployment;
import de.egore911.versioning.persistence.model.Project;
import de.egore911.versioning.persistence.model.Server;
import de.egore911.versioning.persistence.model.Version;
import de.egore911.versioning.util.VersionUtil;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class DeploymentCalculator implements Serializable {

	private static final long serialVersionUID = 488865407571381111L;

	private static final Comparator<Version> COMPARATOR_BY_PROJECT = new Comparator<Version>() {

		@Override
		public int compare(Version o1, Version o2) {
			return o1.getProject().compareTo(o2.getProject());
		}
	};

	public List<Version> getDeployedVersions(Server server) {
		List<Version> result = new ArrayList<>();
		DeploymentDao deploymentDao = BeanProvider.getContextualReference(DeploymentDao.class);
		for (Deployment deployment : deploymentDao
				.getCurrentDeployments(server)) {
			result.add(deployment.getVersion());
		}
		Collections.sort(result, COMPARATOR_BY_PROJECT);
		return result;
	}

	public List<Version> getDeployableVersions(Server server) {

		DeploymentDao deploymentDao = BeanProvider.getContextualReference(DeploymentDao.class);
		ProjectDao projectDao = BeanProvider.getContextualReference(ProjectDao.class);

		Map<Project, Version> currentlyDeployedVersions = new HashMap<>();
		List<Deployment> currentDeployments = deploymentDao
				.getCurrentDeployments(server);
		for (Deployment currentDeployment : currentDeployments) {
			currentlyDeployedVersions.put(currentDeployment.getVersion()
					.getProject(), currentDeployment.getVersion());
		}

		List<Project> configuredProjects = projectDao
				.getConfiguredProjects(server);
		List<Version> result = new ArrayList<>();
		for (Project configuredProject : configuredProjects) {
			Version latest;
			try {
				latest = VersionUtil.getLatestVersion(configuredProject
						.getVersions());
			} catch (Exception e) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				if (facesContext != null) {
					FacesMessage message = new FacesMessage(
							FacesMessage.SEVERITY_ERROR, e.getMessage(),
							e.getMessage());
					facesContext.addMessage("main", message);
				}

				latest = null;
			}

			// No version for a project yet
			if (latest == null) {
				continue;
			}

			// Test if latest is more up to date than current
			Version current = currentlyDeployedVersions.get(configuredProject);
			if (current == null || latest.isNewerThan(current)) {
				result.add(latest);
			}
		}
		Collections.sort(result, COMPARATOR_BY_PROJECT);
		return result;
	}

}
