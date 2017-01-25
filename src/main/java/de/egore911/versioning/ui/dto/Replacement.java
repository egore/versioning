package de.egore911.versioning.ui.dto;

import de.egore911.appframework.ui.dto.AbstractDto;

public class Replacement extends AbstractDto {

	private String variable;
	private String value;

	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
