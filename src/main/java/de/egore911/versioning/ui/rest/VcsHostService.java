package de.egore911.versioning.ui.rest;

import java.util.ResourceBundle;

import javax.annotation.Nonnull;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.secnod.shiro.jaxrs.Auth;

import de.egore911.appframework.ui.exceptions.BadArgumentException;
import de.egore911.appframework.ui.rest.AbstractResourceService;
import de.egore911.versioning.persistence.dao.VcsHostDao;
import de.egore911.versioning.persistence.model.VcsHostEntity;
import de.egore911.versioning.persistence.selector.VcsHostSelector;
import de.egore911.versioning.ui.dto.VcsHost;
import de.egore911.versioning.ui.logic.VcshostUtil;
import de.egore911.versioning.util.SessionUtil;

@Path("vcs_host")
public class VcsHostService
		extends AbstractResourceService<VcsHost, VcsHostEntity> {

	@Override
	protected Class<VcsHost> getDtoClass() {
		return VcsHost.class;
	}

	@Override
	protected Class<VcsHostEntity> getEntityClass() {
		return VcsHostEntity.class;
	}

	@Override
	protected VcsHostSelector getSelector(Subject subject) {
		return new VcsHostSelector();
	}

	@Override
	protected VcsHostDao getDao() {
		return new VcsHostDao();
	}

	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") @Nonnull Integer id,
			@Auth Subject subject) {
		VcshostUtil.checkDeletable(new VcsHostDao().findById(id));
		super.delete(id, subject);
	}

	@Override
	protected void validate(VcsHost dto, VcsHostEntity entity) {
		super.validate(dto, entity);

		if (StringUtils.isNotEmpty(entity.getPassword())
				|| StringUtils.isNotEmpty(dto.getPasswordVerify())) {
			if (entity.getPassword() != null
					&& !entity.getPassword().equals(dto.getPasswordVerify())) {
				ResourceBundle bundle = SessionUtil.getBundle();
				throw new BadArgumentException(
						bundle.getString("passwords_dont_match"));
			}
		}
	}
}
