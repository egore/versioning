package de.egore911.versioning.persistence.dao;

import de.egore911.versioning.persistence.model.ActionCheckoutEntity;
import de.egore911.versioning.persistence.selector.ActionCheckoutSelector;

public class ActionCheckoutDao
		extends AbstractActionDao<ActionCheckoutEntity> {

	@Override
	protected Class<ActionCheckoutEntity> getEntityClass() {
		return ActionCheckoutEntity.class;
	}

	@Override
	protected ActionCheckoutSelector createSelector() {
		return new ActionCheckoutSelector();
	}

}
