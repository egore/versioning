package de.egore911.versioning.persistence.selector;

import de.egore911.appframework.testing.persistence.selector.AbstractResourceSelectorTest;
import de.egore911.versioning.persistence.model.TagTransformerEntity;

public class TagTransformerSelectorTest
		extends AbstractResourceSelectorTest<TagTransformerEntity> {

	@Override
	protected TagTransformerSelector getSelector() {
		return new TagTransformerSelector();
	}

}
