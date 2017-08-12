package de.egore911.versioning.persistence.selector;

import de.egore911.appframework.testing.persistence.selector.AbstractResourceSelectorTest;
import de.egore911.versioning.persistence.model.ServerEntity;

public class ServerSelectorTest
		extends AbstractResourceSelectorTest<ServerEntity> {

	@Override
	protected ServerSelector getSelector() {
		return new ServerSelector();
	}

}
