package de.egore911.versioning.ui.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.Days;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import de.egore911.versioning.persistence.dao.DeploymentDao;
import de.egore911.versioning.persistence.model.Deployment;
import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.Project;
import de.egore911.versioning.persistence.model.Server;
import de.egore911.versioning.util.security.RequiresPermission;

@Named
@RequestScoped
@RequiresPermission(Permission.ADMIN_SETTINGS)
public class DeploymentHistoryBean {

	private Map<Server, DeploymentHistory> historyCache = new HashMap<>(1);

	@Inject
	private DeploymentDao deploymentDao;

	public class DeploymentHistory {
		private List<DeploymentHistoryEntry> entries = new ArrayList<>(0);
		private LocalDateTime minDate = LocalDateTime.now();
		private LocalDateTime maxDate = LocalDateTime.now();

		public List<DeploymentHistoryEntry> getEntries() {
			return entries;
		}

		public void setEntries(List<DeploymentHistoryEntry> entries) {
			this.entries = entries;
		}

		public LocalDateTime getMinDate() {
			return minDate;
		}

		public void setMinDate(LocalDateTime minDate) {
			this.minDate = minDate;
		}

		public LocalDateTime getMaxDate() {
			return maxDate;
		}

		public void setMaxDate(LocalDateTime maxDate) {
			this.maxDate = maxDate;
		}

		public int getLeft() {
			return 0;
		}

		public int getRight() {
			return Days.daysBetween(minDate, maxDate).getDays();
		}

	}

	public class DeploymentDuration {
		private Deployment deployment;
		private int offset;
		private int length;
		private String color;

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

	public class DeploymentHistoryEntry {
		private Project project;
		private List<DeploymentDuration> deploymentDurations = new ArrayList<>(
				0);

		public DeploymentHistoryEntry(Project project) {
			this.project = project;
		}

		public Project getProject() {
			return project;
		}

		public void setProject(Project project) {
			this.project = project;
		}

		public List<DeploymentDuration> getDeploymentDurations() {
			return deploymentDurations;
		}

		public void setDeploymentDurations(
				List<DeploymentDuration> deploymentDurations) {
			this.deploymentDurations = deploymentDurations;
		}

	}

	public DeploymentHistory getDeploymentHistory(Server server) {
		DeploymentHistory result = historyCache.get(server);
		if (result != null) {
			return result;
		}

		List<Deployment> allDeployments = deploymentDao.getAllDeployments(
				server, LocalDateTime.now().minusYears(2));

		result = new DeploymentHistory();

		Map<Project, DeploymentHistoryEntry> cache = new HashMap<>();
		for (Deployment d : allDeployments) {
			DeploymentHistoryEntry entry = cache.get(d.getVersion()
					.getProject());
			if (entry == null) {
				entry = new DeploymentHistoryEntry(d.getVersion().getProject());
				result.getEntries().add(entry);
				cache.put(d.getVersion().getProject(), entry);
			}
			if (d.getUndeployment() != null) {
				if (result.maxDate.isBefore(d.getUndeployment())) {
					result.maxDate = d.getUndeployment();
				}
				if (result.maxDate.isBefore(d.getDeployment())) {
					result.maxDate = d.getUndeployment();
				}
			}
			if (d.getDeployment() != null) {
				if (result.minDate.isAfter(d.getDeployment())) {
					result.minDate = d.getDeployment();
				}
				if (result.minDate.isAfter(d.getDeployment())) {
					result.minDate = d.getDeployment();
				}
			}
			entry.getDeploymentDurations().add(new DeploymentDuration(d));
		}

		for (DeploymentHistoryEntry entry : result.getEntries()) {
			Collections.sort(entry.getDeploymentDurations(),
					new Comparator<DeploymentDuration>() {
						@Override
						public int compare(DeploymentDuration o1,
								DeploymentDuration o2) {
							return o1
									.getDeployment()
									.getDeployment()
									.compareTo(
											o2.getDeployment().getDeployment());
						}
					});
			for (DeploymentDuration deploymentDuration : entry
					.getDeploymentDurations()) {
				if (deploymentDuration.getDeployment().getDeployment() == null) {
					deploymentDuration.offset = 0;
				} else {
					deploymentDuration.offset = Days.daysBetween(
							result.getMinDate(),
							deploymentDuration.getDeployment().getDeployment())
							.getDays();
				}
				if (deploymentDuration.getDeployment().getUndeployment() == null) {
					deploymentDuration.length = result.getRight();
				} else {
					deploymentDuration.length = result.getRight()
							- Days.daysBetween(
									deploymentDuration.getDeployment()
											.getUndeployment(),
									result.getMaxDate()).getDays();
				}
				deploymentDuration.length -= deploymentDuration.offset;
				deploymentDuration.color = randomColor(deploymentDuration.deployment);
			}
		}
		Collections.sort(result.getEntries(),
				new Comparator<DeploymentHistoryEntry>() {
					@Override
					public int compare(DeploymentHistoryEntry o1,
							DeploymentHistoryEntry o2) {
						return o1.getProject().getName()
								.compareTo(o2.getProject().getName());
					}
				});

		historyCache.put(server, result);

		return result;
	}

	private String randomColor(Deployment d) {
		Random random = new Random(d.getVersion().getId().hashCode()
				| d.getVersion().getProject().getId());
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

	public String readableDate(LocalDateTime localDateTime) {
		if (localDateTime == null) {
			return null;
		}
		return DateTimeFormat.forStyle("SS").print(localDateTime);
	}

}
