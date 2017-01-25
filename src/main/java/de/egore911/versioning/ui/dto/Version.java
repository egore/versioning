package de.egore911.versioning.ui.dto;

import java.time.LocalDateTime;
import java.util.List;

import de.egore911.appframework.ui.dto.AbstractDto;

public class Version extends AbstractDto {

	private Project project;
	private String vcsTag;
	private List<Integer> deploymentIds;
	private String createdBy;
	private LocalDateTime created;
	private String transformedVcsTag;

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

	public List<Integer> getDeploymentIds() {
		return deploymentIds;
	}

	public void setDeploymentIds(List<Integer> deploymentIds) {
		this.deploymentIds = deploymentIds;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public String getTransformedVcsTag() {
		return transformedVcsTag;
	}

	public void setTransformedVcsTag(String transformedVcsTag) {
		this.transformedVcsTag = transformedVcsTag;
	}

}
