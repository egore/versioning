package de.egore911.versioning.ui.rest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.egore911.appframework.ui.rest.AbstractService;
import de.egore911.versioning.persistence.dao.DeploymentDao;
import de.egore911.versioning.persistence.dao.ServerDao;
import de.egore911.versioning.persistence.model.DeploymentEntity;
import de.egore911.versioning.persistence.model.ServerEntity;
import de.egore911.versioning.ui.dto.Deployment;
import de.egore911.versioning.ui.dto.DeploymentDuration;
import de.egore911.versioning.ui.dto.DeploymentHistory;
import de.egore911.versioning.ui.dto.DeploymentHistoryEntry;
import de.egore911.versioning.ui.dto.Project;

@Path("history")
//@RequiresPermission(Permission.ADMIN_SETTINGS)
public class DeploymentHistoryService extends AbstractService {

	private Map<ServerEntity, DeploymentHistory> historyCache = new HashMap<>(1);

	@Path("server/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public DeploymentHistory getServerHistory(@PathParam("id") Integer serverId) {
		ServerEntity server = new ServerDao().findById(serverId);
		DeploymentHistory result = historyCache.get(server);
		if (result != null) {
			return result;
		}

		DeploymentDao deploymentDao = new DeploymentDao();
		List<DeploymentEntity> allDeployments = deploymentDao
				.getAllDeployments(server, LocalDateTime.now().minusYears(2));

		result = new DeploymentHistory();

		Map<Integer, DeploymentHistoryEntry> cache = new HashMap<>();
		for (DeploymentEntity d : allDeployments) {
			DeploymentHistoryEntry entry = cache.get(d.getVersion()
					.getProject().getId());
			if (entry == null) {
				entry = new DeploymentHistoryEntry(getMapper().map(d.getVersion().getProject(), Project.class));
				result.getEntries().add(entry);
				cache.put(d.getVersion().getProject().getId(), entry);
			}
			if (d.getUndeployment() != null) {
				if (result.getMaxDate().isBefore(d.getUndeployment())) {
					result.setMaxDate(d.getUndeployment());
				}
				if (result.getMaxDate().isBefore(d.getDeployment())) {
					result.setMaxDate(d.getUndeployment());
				}
			}
			if (d.getDeployment() != null) {
				if (result.getMinDate().isAfter(d.getDeployment())) {
					result.setMinDate(d.getDeployment());
				}
				if (result.getMinDate().isAfter(d.getDeployment())) {
					result.setMinDate(d.getDeployment());
				}
			}
			entry.getDeploymentDurations().add(new DeploymentDuration(getMapper().map(d, Deployment.class)));
		}

		for (DeploymentHistoryEntry entry : result.getEntries()) {
			Collections.sort(entry.getDeploymentDurations(),
					(o1, o2) -> o1.getDeployment().getDeployment().compareTo(o2.getDeployment().getDeployment()));
			for (DeploymentDuration deploymentDuration : entry.getDeploymentDurations()) {
				if (deploymentDuration.getDeployment().getDeployment() == null) {
					deploymentDuration.setOffset(0);
				} else {
					deploymentDuration.setOffset((int) ChronoUnit.DAYS.between(result.getMinDate(), deploymentDuration.getDeployment().getDeployment()));
				}
				if (deploymentDuration.getDeployment().getUndeployment() == null) {
					deploymentDuration.setLength(result.getRight());
				} else {
					deploymentDuration.setLength((int) (result.getRight() - ChronoUnit.DAYS.between(deploymentDuration.getDeployment().getUndeployment(), result.getMaxDate())));
				}
				deploymentDuration.setLength(deploymentDuration.getLength() - deploymentDuration.getOffset());
				deploymentDuration.setColor(randomColor(deploymentDuration.getDeployment()));
			}
		}
		Collections.sort(result.getEntries(),
				(o1, o2) -> o1.getProject().getName().compareTo(o2.getProject().getName()));

		historyCache.put(server, result);

		return result;
	}

	private String randomColor(Deployment d) {
		Random random = new Random(d.getVersion().getId().hashCode() | d.getVersion().getProject().getId());
		StringBuilder builder = new StringBuilder();
		builder.append('#');
		String r = Integer.toHexString(random.nextInt(255));
		if (r.length() < 2) {
			builder.append('0');
		}
		builder.append(r);
		String g = Integer.toHexString(random.nextInt(255));
		if (g.length() < 2) {
			builder.append('0');
		}
		builder.append(g);
		String b = Integer.toHexString(random.nextInt(255));
		if (b.length() < 2) {
			builder.append('0');
		}
		builder.append(b);
		return builder.toString();
	}

}
