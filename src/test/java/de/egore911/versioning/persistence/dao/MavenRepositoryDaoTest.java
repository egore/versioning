package de.egore911.versioning.persistence.dao;

import de.egore911.appframework.testing.persistence.dao.AbstractDaoCRUDTest;
import de.egore911.versioning.persistence.model.MavenRepositoryEntity;

import java.util.UUID;

public class MavenRepositoryDaoTest extends AbstractDaoCRUDTest<MavenRepositoryEntity> {

    @Override
    protected MavenRepositoryEntity createFixture() {
        MavenRepositoryEntity mavenRepository = new MavenRepositoryEntity();
        mavenRepository.setName(UUID.randomUUID().toString());
        mavenRepository.setBaseUrl(UUID.randomUUID().toString());
        return mavenRepository;
    }

    @Override
    protected void modifyFixture(MavenRepositoryEntity mavenRepository) {
        mavenRepository.setBaseUrl(UUID.randomUUID().toString());
    }

    @Override
    protected MavenRepositoryDao getDao() {
        return new MavenRepositoryDao();
    }

}
