package de.egore911.versioning.ui.rest;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.secnod.shiro.jaxrs.Auth;

import de.egore911.appframework.ui.exceptions.BadStateException;
import de.egore911.appframework.ui.rest.AbstractResourceService;
import de.egore911.persistence.selector.AbstractSelector;
import de.egore911.persistence.util.EntityManagerUtil;
import de.egore911.versioning.persistence.dao.VerificationDao;
import de.egore911.versioning.persistence.model.UsedArtifactEntity;
import de.egore911.versioning.persistence.model.VerificationEntity;
import de.egore911.versioning.persistence.selector.VerificationSelector;
import de.egore911.versioning.ui.dto.Verification;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.MappingContextFactory;
import ma.glasnost.orika.impl.UtilityResolver;

@Path("verification")
public class VerificationService
		extends AbstractResourceService<Verification, VerificationEntity> {

	@Override
	protected Class<Verification> getDtoClass() {
		return Verification.class;
	}

	@Override
	protected Class<VerificationEntity> getEntityClass() {
		return VerificationEntity.class;
	}

	@Override
	protected VerificationSelector getSelector(Subject subject) {
		return new VerificationSelector();
	}

	@Override
	protected VerificationDao getDao() {
		return new VerificationDao();
	}

	@Override
	@RequiresPermissions("SHOW_VERIFICATIONS")
	public Verification getById(Integer id, Subject subject) {
		return super.getById(id, subject);
	}

	@Override
	@RequiresPermissions("SHOW_VERIFICATIONS")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Verification> getByIds(@QueryParam("ids") List<Integer> ids,
			@QueryParam("offset") Integer offset,
			@QueryParam("limit") Integer limit,
			@QueryParam("sortColumn") String sortColumn,
			@QueryParam("ascending") Boolean ascending,
			@QueryParam("search") String search, @Auth Subject subject,
			@Context HttpServletResponse response) {
		MappingContextFactory contextFactory = UtilityResolver
				.getDefaultMappingContextFactory();
		MappingContext context = contextFactory.getContext();
		context.setProperty("includeUsedBy", Boolean.FALSE);
		try {
			AbstractSelector<VerificationEntity> selector = getSelector(subject)
					.withIds(ids).withSearch(search).withSortColumn(sortColumn)
					.withAscending(ascending);
			List<VerificationEntity> entities = selector.withOffset(offset)
					.withLimit(limit).findAll();
			if (offset != null || limit != null) {
				response.setHeader("Result-Count",
						Long.toString(selector.count()));
			}
			return getMapper().mapAsList(entities, getDtoClass(), context);
		} finally {
			contextFactory.release(context);
		}
	}

	@Override
	@RequiresPermissions("ADMIN_VERIFICATIONS")
	public Verification create(Verification verification, @Auth Subject subject) {
		return super.create(verification, subject);
	}

	@Override
	@Consumes(MediaType.APPLICATION_JSON)
	public void update(@PathParam("id") @Nonnull Integer id, Verification t,
			@Auth Subject subject) {
		throw new BadStateException("Verifications cannot be modified");
	}

	@Override
	@RequiresPermissions("ADMIN_VERIFICATIONS")
	public void delete(Integer id, Subject subject) {
		super.delete(id, subject);
	}

	@GET
	@Path("suggest/groupId/{input}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> suggestGroupId(@PathParam("input") String input) {
		return EntityManagerUtil.getEntityManager()
				.createQuery(
						"select distinct v.groupId from Verification v where v.groupId like :like order by v.groupId",
						String.class)
				.setParameter("like", '%' + input + '%').getResultList();
	}

	@GET
	@Path("suggest/artifactId/{input}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> suggestArtifactId(@PathParam("input") String input) {
		return EntityManagerUtil.getEntityManager()
				.createQuery(
						"select distinct v.artifactId from Verification v where v.artifactId like :like order by v.artifactId",
						String.class)
				.setParameter("like", '%' + input + '%').getResultList();
	}

	@GET
	@Path("suggest/version/{input}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> suggestVersion(@PathParam("input") String input) {
		return EntityManagerUtil.getEntityManager()
				.createQuery(
						"select distinct v.version from Verification v where v.version like :like order by v.version",
						String.class)
				.setParameter("like", '%' + input + '%').getResultList();
	}

	@GET
	@Path("suggest/packaging/{input}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> suggestPackaging(@PathParam("input") String input) {
		return EntityManagerUtil.getEntityManager()
				.createQuery(
						"select distinct v.packaging from Verification v where v.packaging like :like order by v.packaging",
						String.class)
				.setParameter("like", '%' + input + '%').getResultList();
	}

	@GET
	@Path("available/{groupId}/{artifactId}/{version}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Verification> getAvailable(@PathParam("groupId") String groupId, @PathParam("artifactId") String artifactId,
										   @PathParam("version") String version, @QueryParam("usedByGroupId") String usedByGroupId,
										   @QueryParam("usedByArtifactId") String usedByArtifactId, @QueryParam("usedByVersion") String usedByVersion) {
		try {
			EntityManagerUtil.getEntityManager().getTransaction().begin();
			List<VerificationEntity> entities = new VerificationSelector()
					.withGroupId(groupId)
					.withArtifactId(artifactId)
					.withVersion(version)
					.findAll();
			if (StringUtils.isNotEmpty(usedByGroupId) && StringUtils.isNotEmpty(usedByArtifactId) && StringUtils.isNotEmpty(usedByVersion)) {
				for (VerificationEntity entity : entities) {
					UsedArtifactEntity existing = null;
					for (UsedArtifactEntity usedArtifact : entity.getUsedBy()) {
						if (usedArtifact.getGroupId().equals(usedByGroupId) &&
								usedArtifact.getArtifactId().equals(usedByArtifactId)) {
							existing = usedArtifact;
							break;
						}
					}
					if (existing == null) {
						existing = new UsedArtifactEntity();
						existing.setVerification(entity);
						existing.setGroupId(usedByGroupId);
						existing.setArtifactId(usedByArtifactId);
						existing.setLastSeen(LocalDateTime.now());
						existing.setVersion(usedByVersion);
						EntityManagerUtil.getEntityManager().persist(existing);
					} else {
						existing.setLastSeen(LocalDateTime.now());
						existing.setVersion(usedByVersion);
					}
				}
			}
			EntityManagerUtil.getEntityManager().getTransaction().commit();
			MappingContextFactory contextFactory = UtilityResolver
					.getDefaultMappingContextFactory();
			MappingContext context = contextFactory.getContext();
			context.setProperty("includeUsedBy", Boolean.FALSE);
			try {
				return getMapper().mapAsList(entities, Verification.class,
						context);
			} finally {
				contextFactory.release(context);
			}
		} finally {
			if (EntityManagerUtil.getEntityManager().getTransaction().isActive()) {
				EntityManagerUtil.getEntityManager().getTransaction().rollback();
			}
		}
	}

}
