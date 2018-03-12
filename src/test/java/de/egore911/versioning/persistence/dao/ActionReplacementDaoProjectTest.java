package de.egore911.versioning.persistence.dao;

import java.util.UUID;

import de.egore911.appframework.testing.persistence.dao.AbstractDaoCRUDTest;
import de.egore911.versioning.persistence.model.ActionReplacementEntity;
import de.egore911.versioning.persistence.model.ProjectEntity;

public class ActionReplacementDaoProjectTest
		extends AbstractDaoCRUDTest<ActionReplacementEntity> {

	@Override
	protected ActionReplacementEntity createFixture() {
		ProjectEntity project = new ProjectDaoTest().createFixture();
		project = new ProjectDao().save(project);

		ActionReplacementEntity actionReplacement = new ActionReplacementEntity();
		actionReplacement.setProject(project);
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
