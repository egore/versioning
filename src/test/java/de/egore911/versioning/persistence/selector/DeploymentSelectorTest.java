package de.egore911.versioning.persistence.selector;

import de.egore911.appframework.testing.persistence.selector.AbstractResourceSelectorTest;
import de.egore911.versioning.persistence.model.DeploymentEntity;

public class DeploymentSelectorTest
		extends AbstractResourceSelectorTest<DeploymentEntity> {

	@Override
	protected DeploymentSelector getSelector() {
		return new DeploymentSelector();
	}

}
