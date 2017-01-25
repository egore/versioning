package de.egore911.versioning.ui.dto;

import java.util.List;

import de.egore911.appframework.ui.dto.AbstractDto;

public class Project extends AbstractDto {

	private String name;
	private String description;
	private Integer vcsHostId;
	private String vcsPath;
	private Integer tagTransformerId;
	private List<Integer> configuredServerIds;
	private boolean deleted;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getVcsHostId() {
		return vcsHostId;
	}

	public void setVcsHostId(Integer vcsHostId) {
		this.vcsHostId = vcsHostId;
	}

	public String getVcsPath() {
		return vcsPath;
	}

	public void setVcsPath(String vcsPath) {
		this.vcsPath = vcsPath;
	}

	public Integer getTagTransformerId() {
		return tagTransformerId;
	}

	public void setTagTransformerId(Integer tagTransformerId) {
		this.tagTransformerId = tagTransformerId;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public List<Integer> getConfiguredServerIds() {
		return configuredServerIds;
	}

	public void setConfiguredServerIds(List<Integer> configuredServerIds) {
		this.configuredServerIds = configuredServerIds;
	}
}
