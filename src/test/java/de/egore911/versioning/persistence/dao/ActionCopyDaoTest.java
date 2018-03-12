package de.egore911.versioning.persistence.dao;

import java.util.UUID;

import de.egore911.appframework.testing.persistence.dao.AbstractDaoCRUDTest;
import de.egore911.versioning.persistence.model.ActionCopyEntity;
import de.egore911.versioning.persistence.model.ProjectEntity;

public class ActionCopyDaoTest extends AbstractDaoCRUDTest<ActionCopyEntity> {

	@Override
	protected ActionCopyEntity createFixture() {
		ProjectEntity project = new ProjectDaoTest().createFixture();
		project = new ProjectDao().save(project);

		ActionCopyEntity actionCopy = new ActionCopyEntity();
		actionCopy.setProject(project);
		actionCopy.setTargetFilename(UUID.randomUUID().toString());
		actionCopy.setTargetPath(UUID.randomUUID().toString());
		return actionCopy;
	}

	@Override
	protected void modifyFixture(ActionCopyEntity actionCopy) {
		actionCopy.setTargetFilename(UUID.randomUUID().toString());
	}

	@Override
	protected ActionCopyDao getDao() {
		return new ActionCopyDao();
	}

}
