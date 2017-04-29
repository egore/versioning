package de.egore911.versioning.ui.dto;

import java.util.List;

import de.egore911.appframework.ui.dto.AbstractDto;

public class ActionReplacement extends AbstractDto {

	private List<String> wildcards;
	private List<Replacement> replacements;
	private List<String> replacementfiles;

	public List<String> getWildcards() {
		return wildcards;
	}

	public void setWildcards(List<String> wildcards) {
		this.wildcards = wildcards;
	}

	public List<Replacement> getReplacements() {
		return replacements;
	}

	public void setReplacements(List<Replacement> replacements) {
		this.replacements = replacements;
	}

	public List<String> getReplacementfiles() {
		return replacementfiles;
	}

	public void setReplacementfiles(List<String> replacementfiles) {
		this.replacementfiles = replacementfiles;
	}

}
