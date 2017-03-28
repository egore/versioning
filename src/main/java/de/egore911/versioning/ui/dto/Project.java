package de.egore911.versioning.ui.dto;

import java.util.List;

import de.egore911.appframework.ui.dto.AbstractDto;

public class Project extends AbstractDto {

	private String name;
	private String description;
	private Integer vcsHostId;
	private String vcsPath;
	private List<ActionCopy> actionCopies;
	private List<ActionExtraction> actionExtractions;
	private List<ActionReplacement> actionReplacements;
	private List<ActionCheckout> actionCheckouts;
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

	public List<ActionCopy> getActionCopies() {
		return actionCopies;
	}

	public void setActionCopies(List<ActionCopy> actionCopies) {
		this.actionCopies = actionCopies;
	}

	public List<ActionExtraction> getActionExtractions() {
		return actionExtractions;
	}

	public void setActionExtractions(List<ActionExtraction> actionExtractions) {
		this.actionExtractions = actionExtractions;
	}

	public List<ActionReplacement> getActionReplacements() {
		return actionReplacements;
	}

	public void setActionReplacements(List<ActionReplacement> actionReplacements) {
		this.actionReplacements = actionReplacements;
	}

	public List<ActionCheckout> getActionCheckouts() {
		return actionCheckouts;
	}

	public void setActionCheckouts(List<ActionCheckout> actionCheckouts) {
		this.actionCheckouts = actionCheckouts;
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
