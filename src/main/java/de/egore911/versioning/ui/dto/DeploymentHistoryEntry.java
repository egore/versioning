package de.egore911.versioning.ui.dto;

import java.util.ArrayList;
import java.util.List;

public class DeploymentHistoryEntry {
	private Project project;
	private List<DeploymentDuration> deploymentDurations = new ArrayList<>(
			0);

	public DeploymentHistoryEntry(Project project) {
		this.project = project;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<DeploymentDuration> getDeploymentDurations() {
		return deploymentDurations;
	}

	public void setDeploymentDurations(
			List<DeploymentDuration> deploymentDurations) {
		this.deploymentDurations = deploymentDurations;
	}

}