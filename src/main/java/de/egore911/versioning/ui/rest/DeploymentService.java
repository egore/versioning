package de.egore911.versioning.ui.rest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.subject.Subject;

import de.egore911.appframework.ui.rest.AbstractResourceService;
import de.egore911.persistence.util.EntityManagerUtil;
import de.egore911.versioning.persistence.dao.DeploymentDao;
import de.egore911.versioning.persistence.dao.ServerDao;
import de.egore911.versioning.persistence.dao.VersionDao;
import de.egore911.versioning.persistence.model.DeploymentEntity;
import de.egore911.versioning.persistence.model.ServerEntity;
import de.egore911.versioning.persistence.model.VersionEntity;
import de.egore911.versioning.persistence.selector.DeploymentSelector;
import de.egore911.versioning.ui.dto.Deployment;
import de.egore911.versioning.ui.dto.Server;
import de.egore911.versioning.ui.dto.ServerVersions;
import de.egore911.versioning.ui.dto.Version;
import de.egore911.versioning.ui.logic.DeploymentCalculator;

@Path("deployment")
public class DeploymentService extends AbstractResourceService<Deployment, DeploymentEntity> {

	private DeploymentCalculator deploymentCalculator = new DeploymentCalculator();

	@Override
	protected Class<Deployment> getDtoClass() {
		return Deployment.class;
	}

	@Override
	protected Class<DeploymentEntity> getEntityClass() {
		return DeploymentEntity.class;
	}

	@Override
	protected DeploymentSelector getSelector(
			Subject subject) {
		return new DeploymentSelector();
	}

	@Override
	protected DeploymentDao getDao() {
		return new DeploymentDao();
	}

	@GET
	@Path("deployable")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ServerVersions> getDeployableVersionsPerServer() {
		List<ServerEntity> servers = new ServerDao().findAll();
		return servers.stream()
				.map(server -> new ServerVersions(getMapper().map(server, Server.class), getMapper().mapAsList(deploymentCalculator.getDeployableVersions(server), Version.class)))
				.collect(Collectors.toList());
	}

	@GET
	@Path("deploy/{serverId}/{versionId}")
	public void deploy(@PathParam("serverId") Integer serverId, @PathParam("versionId") Integer versionId) {
		ServerEntity server = new ServerDao().findById(serverId);
		VersionEntity version = new VersionDao().findById(versionId);
		deploy(server, version);
	}

	@GET
	@Path("deploy/{serverId}")
	public String deployAll(@PathParam("serverId") Integer serverId) {
		ServerEntity server = new ServerDao().findById(serverId);

		List<VersionEntity> versions = deploymentCalculator.getDeployableVersions(server);
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		try {
			for (VersionEntity version : versions) {
				deploy(server, version);
			}
			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
		return "";
	}

	private void deploy(ServerEntity server, VersionEntity version) {
		DeploymentDao deploymentDao = new DeploymentDao();
		DeploymentEntity currentDeployment = deploymentDao.getCurrentDeployment(
				server, version.getProject());

		DeploymentEntity newDeployment = new DeploymentEntity();
		newDeployment.setVersion(version);
		newDeployment.setServer(server);
		newDeployment.setDeployment(LocalDateTime.now());
		deploymentDao.replace(currentDeployment, newDeployment);
	}

}
