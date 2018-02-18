package de.egore911.versioning.persistence.dao;

import de.egore911.appframework.testing.persistence.dao.AbstractDaoCRUDTest;
import de.egore911.versioning.persistence.model.DeploymentEntity;
import de.egore911.versioning.persistence.model.ServerEntity;
import de.egore911.versioning.persistence.model.VersionEntity;

import java.time.LocalDateTime;

public class DeploymentDaoTest extends AbstractDaoCRUDTest<DeploymentEntity> {

    @Override
    protected DeploymentEntity createFixture() {
        ServerEntity server = new ServerDaoTest().createFixture();
        server = new ServerDao().save(server);

        VersionEntity version = new VersionDaoTest().createFixture();
        version = new VersionDao().save(version);

        DeploymentEntity deployment = new DeploymentEntity();
        deployment.setDeployment(LocalDateTime.now());
        deployment.setServer(server);
        deployment.setVersion(version);
        return deployment;
    }

    @Override
    protected void modifyFixture(DeploymentEntity project) {
        project.setDeployment(LocalDateTime.now());
    }

    @Override
    protected DeploymentDao getDao() {
        return new DeploymentDao();
    }

}
