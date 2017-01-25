package de.egore911.versioning.ui.dto;

import de.egore911.appframework.ui.dto.AbstractDto;

public class MavenRepository extends AbstractDto {

	private String name;
	private String baseUrl;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

}
