package de.egore911.versioning.persistence.selector;

import de.egore911.appframework.testing.persistence.selector.AbstractResourceSelectorTest;
import de.egore911.versioning.persistence.model.MavenRepositoryEntity;

public class MavenRepositorySelectorTest
		extends AbstractResourceSelectorTest<MavenRepositoryEntity> {

	@Override
	protected MavenRepositorySelector getSelector() {
		return new MavenRepositorySelector();
	}

}
