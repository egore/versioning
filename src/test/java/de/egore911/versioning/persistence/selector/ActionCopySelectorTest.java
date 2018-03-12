package de.egore911.versioning.persistence.selector;

import de.egore911.appframework.persistence.selector.AbstractResourceSelector;
import de.egore911.appframework.testing.persistence.selector.AbstractResourceSelectorTest;
import de.egore911.versioning.persistence.model.ActionCopyEntity;

public class ActionCopySelectorTest extends AbstractResourceSelectorTest<ActionCopyEntity> {

	@Override
	protected AbstractResourceSelector<ActionCopyEntity> getSelector() {
		return new ActionCopySelector();
	}

}
