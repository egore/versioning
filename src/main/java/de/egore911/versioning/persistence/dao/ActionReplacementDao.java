package de.egore911.versioning.persistence.dao;

import de.egore911.versioning.persistence.model.ActionReplacementEntity;
import de.egore911.versioning.persistence.selector.ActionReplacementSelector;

public class ActionReplacementDao
		extends AbstractActionDao<ActionReplacementEntity> {

	@Override
	protected Class<ActionReplacementEntity> getEntityClass() {
		return ActionReplacementEntity.class;
	}

	@Override
	protected ActionReplacementSelector createSelector() {
		return new ActionReplacementSelector();
	}

}
