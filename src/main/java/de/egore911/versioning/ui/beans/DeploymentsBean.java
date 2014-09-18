package de.egore911.versioning.ui.beans;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.joda.time.LocalDateTime;

import de.egore911.versioning.persistence.dao.DeploymentDao;
import de.egore911.versioning.persistence.dao.ServerDao;
import de.egore911.versioning.persistence.model.Deployment;
import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.Server;
import de.egore911.versioning.persistence.model.Version;
import de.egore911.versioning.ui.logic.DeploymentCalculator;
import de.egore911.versioning.util.security.RequiresPermission;

@Named
@RequestScoped
@RequiresPermission({ Permission.CREATE_VERSIONS, Permission.DEPLOY })
public class DeploymentsBean {

	@Inject
	private DeploymentCalculator deploymentCalculator;
	@Inject
	private DeploymentDao deploymentDao;
	@Inject
	private ServerDao serverDao;

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
		List<Server> servers = serverDao.findAll();
		List<ServerVersions> result = new ArrayList<>();
		for (Server server : servers) {
			result.add(new ServerVersions(server, deploymentCalculator
					.getDeployableVersions(server)));
		}
		return result;
	}

	public String deploy(Server server, Version version) {
		Deployment currentDeployment = deploymentDao.getCurrentDeployment(
				server, version.getProject());

		Deployment newDeployment = new Deployment();
		newDeployment.setVersion(version);
		newDeployment.setServer(server);
		newDeployment.setDeployment(LocalDateTime.now());
		deploymentDao.replace(currentDeployment, newDeployment);
		return "";
	}

	@Transactional
	public String deployAll(Server server) {
		List<Version> versions = deploymentCalculator
				.getDeployableVersions(server);
		for (Version version : versions) {
			deploy(server, version);
		}
		return "";
	}

}
