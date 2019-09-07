/*
 * Copyright 2013-2017  Christoph Brill <egore911@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package de.egore911.versioning.util.listener;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import de.egore911.appframework.util.FactoryHolder;
import de.egore911.appframework.util.listener.AbstractStartupListener;
import de.egore911.versioning.persistence.dao.ActionExtractionDao;
import de.egore911.versioning.persistence.dao.DeploymentDao;
import de.egore911.versioning.persistence.dao.MavenRepositoryDao;
import de.egore911.versioning.persistence.dao.ProjectDao;
import de.egore911.versioning.persistence.dao.ServerDao;
import de.egore911.versioning.persistence.dao.TagTransformerDao;
import de.egore911.versioning.persistence.dao.VcsHostDao;
import de.egore911.versioning.persistence.model.AbstractActionEntity;
import de.egore911.versioning.persistence.model.AbstractRemoteActionEntity;
import de.egore911.versioning.persistence.model.ActionCopyEntity;
import de.egore911.versioning.persistence.model.ActionExtractionEntity;
import de.egore911.versioning.persistence.model.DeploymentEntity;
import de.egore911.versioning.persistence.model.ExtractionEntity;
import de.egore911.versioning.persistence.model.MavenArtifactEntity;
import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.ProjectEntity;
import de.egore911.versioning.persistence.model.ServerEntity;
import de.egore911.versioning.persistence.model.VcsHostEntity;
import de.egore911.versioning.persistence.model.VerificationEntity;
import de.egore911.versioning.persistence.model.VersionEntity;
import de.egore911.versioning.ui.dto.AbstractAction;
import de.egore911.versioning.ui.dto.AbstractRemoteAction;
import de.egore911.versioning.ui.dto.ActionCopy;
import de.egore911.versioning.ui.dto.ActionExtraction;
import de.egore911.versioning.ui.dto.Extraction;
import de.egore911.versioning.ui.dto.MavenArtifact;
import de.egore911.versioning.ui.dto.Project;
import de.egore911.versioning.ui.dto.Server;
import de.egore911.versioning.ui.dto.UsedArtifact;
import de.egore911.versioning.ui.dto.VcsHost;
import de.egore911.versioning.ui.dto.Verification;
import de.egore911.versioning.ui.dto.Version;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;

/**
 * Listener executed during startup, responsible for setting up the
 * java.util.Logging->SLF4J bridge and updating the database.
 *
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class StartupListener extends AbstractStartupListener {

	static {
		FactoryHolder.MAPPER_FACTORY
				.classMap(ProjectEntity.class, Project.class)
				.byDefault()
				.customize(new CustomMapper<ProjectEntity, Project>() {
					@Override
					public void mapAtoB(ProjectEntity a, Project b, MappingContext context) {
						if (a.getVcsHost() != null) {
							b.setVcsHostId(a.getVcsHost().getId());
						} else {
							b.setVcsHostId(null);
						}
						if (a.getTagTransformer() != null) {
							b.setTagTransformerId(a.getTagTransformer().getId());
						} else {
							b.setTagTransformerId(null);
						}
						if (a.getConfiguredServers() != null) {
							b.setConfiguredServerIds(a.getConfiguredServers().stream().map(ServerEntity::getId).collect(Collectors.toList()));
						} else {
							b.setConfiguredServerIds(Collections.emptyList());
						}
					}

					@Override
					public void mapBtoA(Project b, ProjectEntity a, MappingContext context) {
						if (b.getVcsHostId() != null) {
							a.setVcsHost(new VcsHostDao().findById(b.getVcsHostId()));
						} else {
							a.setVcsHost(null);
						}
						if (b.getTagTransformerId() != null) {
							a.setTagTransformer(new TagTransformerDao().findById(b.getTagTransformerId()));
						} else {
							a.setTagTransformer(null);
						}
						if (CollectionUtils.isNotEmpty(b.getConfiguredServerIds())) {
							List<ServerEntity> projects = new ServerDao().findByIds(b.getConfiguredServerIds());
							if (a.getConfiguredServers() == null) {
								a.setConfiguredServers(projects);
							} else {
								a.getConfiguredServers().clear();
								a.getConfiguredServers().addAll(projects);
							}
						} else {
							if (a.getConfiguredServers() != null) {
								a.getConfiguredServers().clear();
							}
						}
					}
				}).register();

		FactoryHolder.MAPPER_FACTORY
				.classMap(VcsHostEntity.class, VcsHost.class)
				.byDefault()
				.customize(new CustomMapper<VcsHostEntity, VcsHost>() {
					@Override
					public void mapAtoB(VcsHostEntity a, VcsHost b, MappingContext context) {
						if (a.getProjects() != null) {
							b.setProjectIds(a.getProjects().stream().map(ProjectEntity::getId).collect(Collectors.toList()));
						} else {
							b.setProjectIds(Collections.emptyList());
						}
					}

					@Override
					public void mapBtoA(VcsHost b, VcsHostEntity a, MappingContext context) {
						if (CollectionUtils.isNotEmpty(b.getProjectIds())) {
							List<ProjectEntity> projects = new ProjectDao().findByIds(b.getProjectIds());
							if (a.getProjects() == null) {
								a.setProjects(projects);
							} else {
								a.getProjects().clear();
								a.getProjects().addAll(projects);
							}
						} else {
							a.setProjects(null);
						}
					}
				}).register();

		FactoryHolder.MAPPER_FACTORY
				.classMap(ServerEntity.class, Server.class)
				.byDefault()
				.customize(new CustomMapper<ServerEntity, Server>() {
					@Override
					public void mapAtoB(ServerEntity a, Server b, MappingContext context) {
						if (a.getVcsHost() != null) {
							b.setVcsHostId(a.getVcsHost().getId());
						} else {
							b.setVcsHostId(null);
						}
						if (a.getConfiguredProjects() != null) {
							b.setConfiguredProjectIds(a.getConfiguredProjects().stream().map(ProjectEntity::getId).collect(Collectors.toList()));
						} else {
							b.setConfiguredProjectIds(Collections.emptyList());
						}
					}

					@Override
					public void mapBtoA(Server b, ServerEntity a, MappingContext context) {
						if (b.getVcsHostId() != null) {
							a.setVcsHost(new VcsHostDao().findById(b.getVcsHostId()));
						} else {
							a.setVcsHost(null);
						}
						if (CollectionUtils.isNotEmpty(b.getConfiguredProjectIds())) {
							List<ProjectEntity> projects = new ProjectDao().findByIds(b.getConfiguredProjectIds());
							if (a.getConfiguredProjects() == null) {
								a.setConfiguredProjects(projects);
							} else {
								a.getConfiguredProjects().clear();
								a.getConfiguredProjects().addAll(projects);
							}
						} else {
							if (a.getConfiguredProjects() != null) {
								a.getConfiguredProjects().clear();
							}
						}
					}
				}).register();

		FactoryHolder.MAPPER_FACTORY
				.classMap(VersionEntity.class, Version.class)
				.byDefault()
				.customize(new CustomMapper<VersionEntity, Version>() {
					@Override
					public void mapAtoB(VersionEntity a, Version b, MappingContext context) {
						if (a.getDeployments() != null) {
							b.setDeploymentIds(a.getDeployments().stream().map(DeploymentEntity::getId).collect(Collectors.toList()));
						} else {
							b.setDeploymentIds(Collections.emptyList());
						}
						b.setCreatedBy(a.getCreatedBy().getName());
						b.setTransformedVcsTag(a.getTransformedVcsTag());
					}
					@Override
					public void mapBtoA(Version b, VersionEntity a, MappingContext context) {
						if (CollectionUtils.isNotEmpty(b.getDeploymentIds())) {
							List<DeploymentEntity> deployments = new DeploymentDao().findByIds(b.getDeploymentIds());
							if (a.getDeployments() == null) {
								a.setDeployments(deployments);
							} else {
								a.getDeployments().clear();
								a.getDeployments().addAll(deployments);
							}
						} else {
							if (a.getDeployments() != null) {
								a.getDeployments().clear();
							}
						}
					}
				}).register();

		FactoryHolder.MAPPER_FACTORY
				.classMap(AbstractActionEntity.class, AbstractAction.class)
				.byDefault()
				.customize(new CustomMapper<AbstractActionEntity, AbstractAction>() {
					@Override
					public void mapAtoB(AbstractActionEntity actionEntity, AbstractAction action, MappingContext context) {
						if (actionEntity.getProject() != null) {
							action.setProjectId(actionEntity.getProject().getId());
						}
						else {
							action.setProjectId(null);
						}
					}

					@Override
					public void mapBtoA(AbstractAction action, AbstractActionEntity actionEntity, MappingContext context) {
						if (action.getProjectId() != null) {
							actionEntity.setProject(new ProjectDao().findById(action.getProjectId()));
						} else {
							actionEntity.setProject(null);
						}
					}
				}).register();

		FactoryHolder.MAPPER_FACTORY
				.classMap(AbstractRemoteActionEntity.class, AbstractRemoteAction.class)
				.use(AbstractActionEntity.class, AbstractAction.class)
				.byDefault()
				.register();

		FactoryHolder.MAPPER_FACTORY
				.classMap(ActionCopyEntity.class, ActionCopy.class)
				.use(AbstractRemoteActionEntity.class, AbstractRemoteAction.class)
				.byDefault()
				.customize(new CustomMapper<ActionCopyEntity, ActionCopy>() {
					@Override
					public void mapBtoA(ActionCopy actionCopy, ActionCopyEntity actionCopyEntity, MappingContext context) {
						if (actionCopyEntity.getMavenArtifact() != null) {
							actionCopyEntity.getMavenArtifact().setActionCopy(actionCopyEntity);
						}
					}
				}).register();

		FactoryHolder.MAPPER_FACTORY
				.classMap(ActionExtractionEntity.class, ActionExtraction.class)
				.use(AbstractRemoteActionEntity.class, AbstractRemoteAction.class)
				.byDefault()
				.customize(new CustomMapper<ActionExtractionEntity, ActionExtraction>() {
					@Override
					public void mapBtoA(ActionExtraction actionExtraction, ActionExtractionEntity actionExtractionEntity, MappingContext context) {
						if (actionExtractionEntity.getMavenArtifact() != null) {
							actionExtractionEntity.getMavenArtifact().setActionExtraction(actionExtractionEntity);
						}
					}
				}).register();

		FactoryHolder.MAPPER_FACTORY
				.classMap(MavenArtifactEntity.class, MavenArtifact.class)
				.byDefault()
				.customize(new CustomMapper<MavenArtifactEntity, MavenArtifact>() {
					@Override
					public void mapAtoB(MavenArtifactEntity mavenArtifactEntity, MavenArtifact mavenArtifact, MappingContext context) {
						if (mavenArtifactEntity.getMavenRepository() != null) {
							mavenArtifact.setMavenRepositoryId(mavenArtifactEntity.getMavenRepository().getId());
						} else {
							mavenArtifact.setMavenRepositoryId(null);
						}
					}

					@Override
					public void mapBtoA(MavenArtifact mavenArtifact, MavenArtifactEntity mavenArtifactEntity, MappingContext context) {
						if (mavenArtifact.getMavenRepositoryId() != null) {
							mavenArtifactEntity.setMavenRepository(new MavenRepositoryDao().findById(mavenArtifact.getMavenRepositoryId()));
						} else {
							mavenArtifactEntity.setMavenRepository(null);
						}
					}
				}).register();

		FactoryHolder.MAPPER_FACTORY
				.classMap(ExtractionEntity.class, Extraction.class)
				.byDefault()
				.customize(new CustomMapper<ExtractionEntity, Extraction>() {
					@Override
					public void mapAtoB(ExtractionEntity extractionEntity, Extraction extraction, MappingContext context) {
						if (extractionEntity.getActionExtraction() != null) {
							extraction.setActionExtractionId(extractionEntity.getActionExtraction().getId());
						} else {
							extraction.setActionExtractionId(null);
						}
					}

					@Override
					public void mapBtoA(Extraction extraction, ExtractionEntity extractionEntity, MappingContext context) {
						if (extraction.getActionExtractionId() != null) {
							extractionEntity.setActionExtraction(new ActionExtractionDao().findById(extraction.getActionExtractionId()));
						} else {
							extractionEntity.setActionExtraction(null);
						}
					}
				}).register();

		FactoryHolder.MAPPER_FACTORY
				.classMap(VerificationEntity.class, Verification.class)
				.exclude("usedBy")
				.byDefault()
				.customize(new CustomMapper<VerificationEntity, Verification>() {
					@Override
					public void mapAtoB(VerificationEntity a, Verification b, MappingContext context) {
						b.setCreatedBy(a.getCreatedBy().getName());
						Boolean includeUsedBy = (Boolean) context.getProperty("includeUsedBy");
						if (!Boolean.FALSE.equals(includeUsedBy)) {
							b.setUsedBy(mapperFacade.mapAsList(a.getUsedBy(), UsedArtifact.class));
						}
					}
				}).register();
		}

	@Override
	protected Enum<?>[] getPermissions() {
		return Permission.values();
	}

	@Override
	protected void initCascadeDeletions() {
	}

}
