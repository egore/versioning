package de.egore911.versioning.persistence.dao;

import de.egore911.versioning.persistence.model.ActionExtractionEntity;
import de.egore911.versioning.persistence.selector.ActionExtractionSelector;

public class ActionExtractionDao
		extends AbstractActionDao<ActionExtractionEntity> {

	@Override
	protected Class<ActionExtractionEntity> getEntityClass() {
		return ActionExtractionEntity.class;
	}

	@Override
	protected ActionExtractionSelector createSelector() {
		return new ActionExtractionSelector();
	}

}
