package de.egore911.versioning.ui.dto;

import de.egore911.appframework.ui.dto.AbstractDto;

public class MavenArtifact extends AbstractDto {

	private String groupId;
	private String artifactId;
	private String packaging;
	private Integer mavenRepositoryId;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public String getPackaging() {
		return packaging;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public Integer getMavenRepositoryId() {
		return mavenRepositoryId;
	}

	public void setMavenRepositoryId(Integer mavenRepositoryId) {
		this.mavenRepositoryId = mavenRepositoryId;
	}
}
