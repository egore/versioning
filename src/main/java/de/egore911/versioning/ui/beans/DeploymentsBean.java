package de.egore911.versioning.ui.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import de.egore911.versioning.persistence.dao.ServerDao;
import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.Server;
import de.egore911.versioning.persistence.model.Version;
import de.egore911.versioning.ui.logic.DeploymentCalculator;
import de.egore911.versioning.util.security.RequiresPermission;

@ManagedBean(name = "deploymentsBean")
@RequestScoped
@RequiresPermission(Permission.USE)
public class DeploymentsBean extends AbstractBean {

	private DeploymentCalculator deploymentCalculator = new DeploymentCalculator();

	public static class ServerVersions {
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

	public List<ServerVersions> getDeployableVersionsPerServer() {
		List<Server> servers = new ServerDao().findAll();
		List<ServerVersions> result = new ArrayList<>();
		for (Server server : servers) {
			result.add(new ServerVersions(server, deploymentCalculator
					.getDeployableVersions(server)));
		}
		return result;
	}

}
