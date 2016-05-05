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
package de.egore911.versioning.ui.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

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
public class DeploymentCalculator {

	private static final Comparator<Version> COMPARATOR_BY_PROJECT = (o1, o2) -> o1.getProject().compareTo(o2.getProject());

	private final ProjectDao projectDao = new ProjectDao();
	private final DeploymentDao deploymentDao = new DeploymentDao();

	public List<Version> getDeployedVersions(Server server) {
		return deploymentDao
				.getCurrentDeployments(server).stream().map(Deployment::getVersion)
				.sorted(COMPARATOR_BY_PROJECT)
				.collect(Collectors.toList());
	}

	public List<Version> getDeployableVersions(Server server) {

		Map<Project, Version> currentlyDeployedVersions = new HashMap<>();
		List<Deployment> currentDeployments = deploymentDao
				.getCurrentDeployments(server);
		for (Deployment currentDeployment : currentDeployments) {
			currentlyDeployedVersions.put(currentDeployment.getVersion()
					.getProject(), currentDeployment.getVersion());
		}

		// Load all projects configured for the server (including deleted ones)
		List<Project> configuredProjects = projectDao
				.getConfiguredProjects(server);
		List<Version> result = new ArrayList<>();
		for (Project configuredProject : configuredProjects) {
			// If the project was deleted, it will no longer be suggested to be deployed
			if (configuredProject.isDeleted()) {
				continue;
			}
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
