package de.egore911.versioning.ui.dto;

import de.egore911.appframework.ui.dto.AbstractDto;

public class Variable extends AbstractDto {

	private String name;
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
