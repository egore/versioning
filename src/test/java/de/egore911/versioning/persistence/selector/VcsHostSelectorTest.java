package de.egore911.versioning.persistence.selector;

import de.egore911.appframework.testing.persistence.selector.AbstractResourceSelectorTest;
import de.egore911.versioning.persistence.model.VcsHostEntity;

public class VcsHostSelectorTest
		extends AbstractResourceSelectorTest<VcsHostEntity> {

	@Override
	protected VcsHostSelector getSelector() {
		return new VcsHostSelector();
	}

}
