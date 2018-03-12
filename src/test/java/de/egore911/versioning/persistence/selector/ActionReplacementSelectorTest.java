package de.egore911.versioning.persistence.selector;

import de.egore911.appframework.persistence.selector.AbstractResourceSelector;
import de.egore911.appframework.testing.persistence.selector.AbstractResourceSelectorTest;
import de.egore911.versioning.persistence.model.ActionReplacementEntity;

public class ActionReplacementSelectorTest extends AbstractResourceSelectorTest<ActionReplacementEntity> {

	@Override
	protected AbstractResourceSelector<ActionReplacementEntity> getSelector() {
		return new ActionReplacementSelector();
	}

}
