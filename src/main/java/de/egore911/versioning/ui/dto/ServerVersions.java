package de.egore911.versioning.ui.dto;

import java.util.List;

public class ServerVersions {
	private final Server server;
	private final List<Version> versions;

	public ServerVersions(Server server, List<Version> versions) {
		this.server = server;
		this.versions = versions;
	}

	public Server getServer() {
		return server;
	}

	public List<Version> getVersions() {
		return versions;
	}
}