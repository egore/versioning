package de.egore911.versioning.ui.dto;

import de.egore911.appframework.ui.dto.AbstractDto;

public class TagTransformer extends AbstractDto {

	private String name;
	private String searchPattern;
	private String replacementPattern;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSearchPattern() {
		return searchPattern;
	}

	public void setSearchPattern(String searchPattern) {
		this.searchPattern = searchPattern;
	}

	public String getReplacementPattern() {
		return replacementPattern;
	}

	public void setReplacementPattern(String replacementPattern) {
		this.replacementPattern = replacementPattern;
	}

}
