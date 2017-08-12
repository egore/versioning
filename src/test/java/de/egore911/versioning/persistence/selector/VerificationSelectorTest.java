package de.egore911.versioning.persistence.selector;

import de.egore911.appframework.testing.persistence.selector.AbstractResourceSelectorTest;
import de.egore911.versioning.persistence.model.VerificationEntity;

public class VerificationSelectorTest
		extends AbstractResourceSelectorTest<VerificationEntity> {

	@Override
	protected VerificationSelector getSelector() {
		return new VerificationSelector();
	}

}
