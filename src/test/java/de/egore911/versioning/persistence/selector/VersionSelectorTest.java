package de.egore911.versioning.persistence.selector;

import de.egore911.appframework.testing.persistence.selector.AbstractResourceSelectorTest;
import de.egore911.versioning.persistence.model.VersionEntity;

public class VersionSelectorTest
		extends AbstractResourceSelectorTest<VersionEntity> {

	@Override
	protected VersionSelector getSelector() {
		return new VersionSelector();
	}

}
