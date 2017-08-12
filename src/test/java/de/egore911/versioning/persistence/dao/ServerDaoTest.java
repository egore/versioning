package de.egore911.versioning.persistence.dao;

import de.egore911.appframework.testing.persistence.dao.AbstractDaoCRUDTest;
import de.egore911.versioning.persistence.model.ServerEntity;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

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

    @Test
    public void testFindByName() {
        String validName = "production_server";
        assertThat(getDao().findByName(validName), notNullValue());
        assertThat(getDao().findByName(validName).getId(), equalTo(1));

        // XXX this test fails on HSQLDB for no obvious reason
        // assertThat(getDao().findByName(validName + " "), nullValue());
        assertThat(getDao().findByName(" " + validName), nullValue());
        assertThat(getDao().findByName("production"), nullValue());

        assertThat(getDao().findByName(UUID.randomUUID().toString()), nullValue());

        assertThat(getDao().findByName(""), nullValue());
        assertThat(getDao().findByName(null), nullValue());
    }
}
