package de.egore911.versioning.persistence.dao;

import java.util.UUID;

import de.egore911.appframework.testing.persistence.dao.AbstractDaoCRUDTest;
import de.egore911.versioning.persistence.model.Vcs;
import de.egore911.versioning.persistence.model.VcsHostEntity;

public class VcsHostDaoTest extends AbstractDaoCRUDTest<VcsHostEntity> {

	@Override
	protected VcsHostEntity createFixture() {
		VcsHostEntity vcsHost = new VcsHostEntity();
		vcsHost.setName(UUID.randomUUID().toString());
		vcsHost.setUri(UUID.randomUUID().toString());
		vcsHost.setVcs(Vcs.git);
		return vcsHost;
	}

	@Override
	protected void modifyFixture(VcsHostEntity vcsHost) {
		vcsHost.setUri(UUID.randomUUID().toString());
	}

	@Override
	protected VcsHostDao getDao() {
		return new VcsHostDao();
	}

}
