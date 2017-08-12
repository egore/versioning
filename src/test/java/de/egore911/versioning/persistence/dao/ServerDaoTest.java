package de.egore911.versioning.persistence.dao;

import de.egore911.appframework.testing.persistence.dao.AbstractDaoCRUDTest;
import de.egore911.versioning.persistence.model.ServerEntity;

import java.util.UUID;

public class ServerDaoTest extends AbstractDaoCRUDTest<ServerEntity> {

    @Override
    protected ServerEntity createFixture() {
        ServerEntity server = new ServerEntity();
        server.setName(UUID.randomUUID().toString());
        server.setDescription(UUID.randomUUID().toString());
        return server;
    }

    @Override
    protected void modifyFixture(ServerEntity server) {
        server.setDescription(UUID.randomUUID().toString());
    }

    @Override
    protected ServerDao getDao() {
        return new ServerDao();
    }

}
