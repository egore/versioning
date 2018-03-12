package de.egore911.versioning.persistence.dao;

import java.util.UUID;

import de.egore911.appframework.testing.persistence.dao.AbstractDaoCRUDTest;
import de.egore911.versioning.persistence.model.ActionReplacementEntity;
import de.egore911.versioning.persistence.model.ServerEntity;

public class ActionReplacementDaoServerTest
		extends AbstractDaoCRUDTest<ActionReplacementEntity> {

	@Override
	protected ActionReplacementEntity createFixture() {
		ServerEntity server = new ServerDaoTest().createFixture();
		server = new ServerDao().save(server);

		ActionReplacementEntity actionReplacement = new ActionReplacementEntity();
		actionReplacement.setServer(server);
		actionReplacement.getWildcards().add(UUID.randomUUID().toString());
		return actionReplacement;
	}

	@Override
	protected void modifyFixture(ActionReplacementEntity actionReplacement) {
		actionReplacement.getWildcards().add(UUID.randomUUID().toString());
	}

	@Override
	protected ActionReplacementDao getDao() {
		return new ActionReplacementDao();
	}

}
