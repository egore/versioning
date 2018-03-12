package de.egore911.versioning.persistence.selector;

import de.egore911.appframework.persistence.selector.AbstractResourceSelector;
import de.egore911.appframework.testing.persistence.selector.AbstractResourceSelectorTest;
import de.egore911.versioning.persistence.model.ActionExtractionEntity;

public class ActionExtractionSelectorTest extends AbstractResourceSelectorTest<ActionExtractionEntity> {

	@Override
	protected AbstractResourceSelector<ActionExtractionEntity> getSelector() {
		return new ActionExtractionSelector();
	}

}
