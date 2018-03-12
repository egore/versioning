package de.egore911.versioning.persistence.dao;

import de.egore911.versioning.persistence.model.ActionCopyEntity;
import de.egore911.versioning.persistence.selector.ActionCopySelector;

public class ActionCopyDao extends AbstractActionDao<ActionCopyEntity> {

	@Override
	protected Class<ActionCopyEntity> getEntityClass() {
		return ActionCopyEntity.class;
	}

	@Override
	protected ActionCopySelector createSelector() {
		return new ActionCopySelector();
	}
}
