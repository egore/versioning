package de.egore911.versioning.ui.rest;

import javax.annotation.Nonnull;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.shiro.subject.Subject;
import org.secnod.shiro.jaxrs.Auth;

import de.egore911.appframework.ui.rest.AbstractResourceService;
import de.egore911.versioning.persistence.dao.MavenRepositoryDao;
import de.egore911.versioning.persistence.model.MavenRepositoryEntity;
import de.egore911.versioning.persistence.selector.MavenRepositorySelector;
import de.egore911.versioning.ui.dto.MavenRepository;
import de.egore911.versioning.ui.logic.MavenrepositoryUtil;

@Path("maven_repository")
public class MavenRepositoryService extends AbstractResourceService<MavenRepository, MavenRepositoryEntity> {

	@Override
	protected Class<MavenRepository> getDtoClass() {
		return MavenRepository.class;
	}

	@Override
	protected Class<MavenRepositoryEntity> getEntityClass() {
		return MavenRepositoryEntity.class;
	}

	@Override
	protected MavenRepositorySelector getSelector(Subject subject) {
		return new MavenRepositorySelector();
	}

	@Override
	protected MavenRepositoryDao getDao() {
		return new MavenRepositoryDao();
	}

	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") @Nonnull Integer id, @Auth Subject subject) {
		MavenrepositoryUtil.checkDeletable(new MavenRepositoryDao().findById(id));
		super.delete(id, subject);
	}
}
