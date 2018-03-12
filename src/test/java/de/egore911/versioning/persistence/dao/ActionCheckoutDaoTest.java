package de.egore911.versioning.persistence.dao;

import java.util.UUID;

import de.egore911.appframework.testing.persistence.dao.AbstractDaoCRUDTest;
import de.egore911.versioning.persistence.model.ActionCheckoutEntity;
import de.egore911.versioning.persistence.model.ProjectEntity;

public class ActionCheckoutDaoTest extends AbstractDaoCRUDTest<ActionCheckoutEntity> {

	@Override
	protected ActionCheckoutEntity createFixture() {
		ProjectEntity project = new ProjectDaoTest().createFixture();
		project = new ProjectDao().save(project);

		ActionCheckoutEntity actionCheckout = new ActionCheckoutEntity();
		actionCheckout.setProject(project);
		actionCheckout.setTargetPath(UUID.randomUUID().toString());
		return actionCheckout;
	}

	@Override
	protected void modifyFixture(ActionCheckoutEntity actionCheckout) {
		actionCheckout.setTargetPath(UUID.randomUUID().toString());
	}

	@Override
	protected ActionCheckoutDao getDao() {
		return new ActionCheckoutDao();
	}

}
