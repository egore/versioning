package de.egore911.versioning.persistence.dao;

import de.egore911.appframework.testing.persistence.dao.AbstractDaoCRUDTest;
import de.egore911.versioning.persistence.model.ProjectEntity;
import de.egore911.versioning.persistence.model.VersionEntity;

import java.util.UUID;

public class VersionDaoTest extends AbstractDaoCRUDTest<VersionEntity> {

    @Override
    protected VersionEntity createFixture() {
        ProjectEntity project = new ProjectDaoTest().createFixture();
        project = new ProjectDao().save(project);

        VersionEntity mavenRepository = new VersionEntity();
        mavenRepository.setProject(project);
        mavenRepository.setVcsTag(UUID.randomUUID().toString());
        return mavenRepository;
    }

    @Override
    protected void modifyFixture(VersionEntity version) {
        version.setVcsTag(UUID.randomUUID().toString());
    }

    @Override
    protected VersionDao getDao() {
        return new VersionDao();
    }

}
