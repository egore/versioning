package de.egore911.versioning.persistence.selector;

import de.egore911.appframework.testing.persistence.selector.AbstractResourceSelectorTest;
import de.egore911.versioning.persistence.model.UsedArtifactEntity;

public class UsedArtifactSelectorTest
		extends AbstractResourceSelectorTest<UsedArtifactEntity> {

	@Override
	protected UsedArtifactSelector getSelector() {
		return new UsedArtifactSelector();
	}

}
