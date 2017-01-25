package de.egore911.versioning.ui.dto;

import java.util.List;

import de.egore911.appframework.ui.dto.AbstractDto;

public class Server extends AbstractDto {

	private String name;
	private String description;
	private String targetdir;
	private Integer vcsHostId;
	private String vcsPath;
	private String targetPath;
	private List<Variable> variables;
	private List<Integer> configuredProjectIds;
	private List<ActionReplacement> actionReplacements;
	private Integer iconId;

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

	public String getTargetdir() {
		return targetdir;
	}

	public void setTargetdir(String targetdir) {
		this.targetdir = targetdir;
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

	public String getTargetPath() {
		return targetPath;
	}

	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}

	public List<Variable> getVariables() {
		return variables;
	}

	public void setVariables(List<Variable> variables) {
		this.variables = variables;
	}

	public List<Integer> getConfiguredProjectIds() {
		return configuredProjectIds;
	}

	public void setConfiguredProjectIds(List<Integer> configuredProjectIds) {
		this.configuredProjectIds = configuredProjectIds;
	}

	public List<ActionReplacement> getActionReplacements() {
		return actionReplacements;
	}

	public void setActionReplacements(
			List<ActionReplacement> actionReplacements) {
		this.actionReplacements = actionReplacements;
	}

	public Integer getIconId() {
		return iconId;
	}
	
	public void setIconId(Integer iconId) {
		this.iconId = iconId;
	}

}
