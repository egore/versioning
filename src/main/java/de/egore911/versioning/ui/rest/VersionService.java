package de.egore911.versioning.ui.rest;

import javax.ws.rs.Path;

import org.apache.shiro.subject.Subject;

import de.egore911.appframework.ui.exceptions.BadArgumentException;
import de.egore911.appframework.ui.rest.AbstractResourceService;
import de.egore911.versioning.persistence.dao.VersionDao;
import de.egore911.versioning.persistence.model.VersionEntity;
import de.egore911.versioning.persistence.selector.VersionSelector;
import de.egore911.versioning.ui.dto.Version;
import de.egore911.versioning.util.vcs.Provider;

@Path("version")
public class VersionService
		extends AbstractResourceService<Version, VersionEntity> {

	@Override
	protected Class<Version> getDtoClass() {
		return Version.class;
	}

	@Override
	protected Class<VersionEntity> getEntityClass() {
		return VersionEntity.class;
	}

	@Override
	protected VersionSelector getSelector(Subject subject) {
		return new VersionSelector();
	}

	@Override
	protected VersionDao getDao() {
		return new VersionDao();
	}

	@Override
	protected void validate(Version dto, VersionEntity entity) {
		super.validate(dto, entity);

		Provider provider = entity.getProject().getProvider();
		if (!provider.tagExists(entity.getVcsTag())) {
			throw new BadArgumentException("Tag not found");
		}

	}

}
