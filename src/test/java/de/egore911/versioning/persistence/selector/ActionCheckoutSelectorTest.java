package de.egore911.versioning.persistence.selector;

import de.egore911.appframework.persistence.selector.AbstractResourceSelector;
import de.egore911.appframework.testing.persistence.selector.AbstractResourceSelectorTest;
import de.egore911.versioning.persistence.model.ActionCheckoutEntity;

public class ActionCheckoutSelectorTest extends AbstractResourceSelectorTest<ActionCheckoutEntity> {

	@Override
	protected AbstractResourceSelector<ActionCheckoutEntity> getSelector() {
		return new ActionCheckoutSelector();
	}

}
