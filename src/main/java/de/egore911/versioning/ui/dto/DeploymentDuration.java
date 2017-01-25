package de.egore911.versioning.ui.dto;

public class DeploymentDuration {
	Deployment deployment;
	int offset;
	int length;
	String color;

	public DeploymentDuration(Deployment deployment) {
		this.deployment = deployment;
	}

	public Deployment getDeployment() {
		return deployment;
	}

	public void setDeployment(Deployment deployment) {
		this.deployment = deployment;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}

}