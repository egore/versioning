package de.egore911.versioning.ui.dto;

import java.time.LocalDateTime;

import de.egore911.appframework.ui.dto.AbstractDto;

public class Deployment extends AbstractDto {

	private Version version;
	private Server server;
	private LocalDateTime deployment;
	private LocalDateTime undeployment;

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public LocalDateTime getDeployment() {
		return deployment;
	}

	public void setDeployment(LocalDateTime deployment) {
		this.deployment = deployment;
	}

	public LocalDateTime getUndeployment() {
		return undeployment;
	}

	public void setUndeployment(LocalDateTime undeployment) {
		this.undeployment = undeployment;
	}

}
