package de.egore911.versioning.persistence.selector;

import de.egore911.appframework.testing.persistence.selector.AbstractResourceSelectorTest;
import de.egore911.versioning.persistence.model.ProjectEntity;

public class ProjectSelectorTest
		extends AbstractResourceSelectorTest<ProjectEntity> {

	@Override
	protected ProjectSelector getSelector() {
		return new ProjectSelector();
	}

}
