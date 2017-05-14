package de.egore911.versioning.persistence.dao;

import java.util.UUID;

import de.egore911.appframework.testing.persistence.dao.AbstractDaoCRUDTest;
import de.egore911.versioning.persistence.model.ProjectEntity;
import de.egore911.versioning.persistence.model.VcsHostEntity;

public class ProjectDaoTest extends AbstractDaoCRUDTest<ProjectEntity> {

	@Override
	protected ProjectEntity createFixture() {
		VcsHostEntity vcsHost = new VcsHostDaoTest().createFixture();
		vcsHost = new VcsHostDao().save(vcsHost);
		
		ProjectEntity project = new ProjectEntity();
		project.setName(UUID.randomUUID().toString());
		project.setDescription(UUID.randomUUID().toString());
		project.setVcsPath(UUID.randomUUID().toString());
		project.setVcsHost(vcsHost);
		return project;
	}

	@Override
	protected void modifyFixture(ProjectEntity project) {
		project.setDescription(UUID.randomUUID().toString());
	}

	@Override
	protected ProjectDao getDao() {
		return new ProjectDao();
	}

}
