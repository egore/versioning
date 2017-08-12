package de.egore911.versioning.persistence.dao;

import de.egore911.appframework.testing.persistence.dao.AbstractDaoCRUDTest;
import de.egore911.versioning.persistence.model.VerificationEntity;

import java.util.UUID;

public class VerificationDaoTest extends AbstractDaoCRUDTest<VerificationEntity> {

    @Override
    protected VerificationEntity createFixture() {
        VerificationEntity verification = new VerificationEntity();
        verification.setGroupId(UUID.randomUUID().toString());
        verification.setArtifactId(UUID.randomUUID().toString());
        verification.setVersion(UUID.randomUUID().toString());
        return verification;
    }

    @Override
    protected void modifyFixture(VerificationEntity verification) {
        verification.setVersion(UUID.randomUUID().toString());
    }

    @Override
    protected VerificationDao getDao() {
        return new VerificationDao();
    }

}
