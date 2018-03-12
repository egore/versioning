package de.egore911.versioning.persistence.dao;

import java.util.UUID;

import de.egore911.appframework.testing.persistence.dao.AbstractDaoCRUDTest;
import de.egore911.versioning.persistence.model.ActionExtractionEntity;
import de.egore911.versioning.persistence.model.ExtractionEntity;
import de.egore911.versioning.persistence.model.ProjectEntity;

public class ActionExtractionDaoTest extends AbstractDaoCRUDTest<ActionExtractionEntity> {

	@Override
	protected ActionExtractionEntity createFixture() {
		ProjectEntity project = new ProjectDaoTest().createFixture();
		project = new ProjectDao().save(project);

		ExtractionEntity extraction = new ExtractionEntity();
		extraction.setSource(UUID.randomUUID().toString());
		extraction.setDestination(UUID.randomUUID().toString());

		ActionExtractionEntity actionExtraction = new ActionExtractionEntity();
		actionExtraction.setProject(project);
		actionExtraction.getExtractions().add(extraction);
		extraction.setActionExtraction(actionExtraction);
		return actionExtraction;
	}

	@Override
	protected void modifyFixture(ActionExtractionEntity actionExtraction) {
		ExtractionEntity extraction = new ExtractionEntity();
		extraction.setSource(UUID.randomUUID().toString());
		extraction.setDestination(UUID.randomUUID().toString());

		actionExtraction.getExtractions().add(extraction);
		extraction.setActionExtraction(actionExtraction);
	}

	@Override
	protected ActionExtractionDao getDao() {
		return new ActionExtractionDao();
	}

}
