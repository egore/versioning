package de.egore911.versioning.ui.rest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.subject.Subject;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.secnod.shiro.jaxrs.Auth;

import de.egore911.appframework.persistence.dao.BinaryDataDao;
import de.egore911.appframework.persistence.model.BinaryDataEntity;
import de.egore911.appframework.ui.exceptions.BadArgumentException;
import de.egore911.appframework.ui.rest.AbstractResourceService;
import de.egore911.versioning.persistence.dao.ServerDao;
import de.egore911.versioning.persistence.model.ActionReplacementEntity;
import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.ProjectEntity;
import de.egore911.versioning.persistence.model.ReplacementEntity;
import de.egore911.versioning.persistence.model.ServerEntity;
import de.egore911.versioning.persistence.model.VariableEntity;
import de.egore911.versioning.persistence.model.VersionEntity;
import de.egore911.versioning.persistence.selector.ServerSelector;
import de.egore911.versioning.ui.dto.Server;
import de.egore911.versioning.ui.dto.Version;
import de.egore911.versioning.ui.logic.DeploymentCalculator;
import de.egore911.versioning.ui.servlets.JsonRenderer;
import de.egore911.versioning.ui.servlets.XmlRenderer;

@Path("server")
public class ServerService
		extends AbstractResourceService<Server, ServerEntity> {

	private final DeploymentCalculator deploymentCalculator = new DeploymentCalculator();

	@Context
	private HttpServletRequest servletRequest;

	@Override
	protected Class<Server> getDtoClass() {
		return Server.class;
	}

	@Override
	protected Class<ServerEntity> getEntityClass() {
		return ServerEntity.class;
	}

	@Override
	protected ServerSelector getSelector(Subject subject) {
		return new ServerSelector();
	}

	@Override
	protected ServerDao getDao() {
		return new ServerDao();
	}

	@Override
	protected ServerEntity mapCreate(Server t) {
		ServerEntity entity = super.mapCreate(t);
		mapUpdate(t, entity);
		return entity;
	}

	@Override
	protected void mapUpdate(Server t, ServerEntity entity) {
		List<ProjectEntity> previousConfiguredProjects = new ArrayList<>(
				entity.getConfiguredProjects());
		super.mapUpdate(t, entity);
		for (ProjectEntity project : previousConfiguredProjects) {
			if (!entity.getConfiguredProjects().contains(project)) {
				project.getConfiguredServers().remove(entity);
			}
		}
		for (ProjectEntity project : entity.getConfiguredProjects()) {
			if (!project.getConfiguredServers().contains(entity)) {
				project.getConfiguredServers().add(entity);
			}
		}
		if (entity.getVariables() != null) {
			for (VariableEntity variable : entity.getVariables()) {
				variable.setServer(entity);
			}
		}
		List<ActionReplacementEntity> actionReplacements = entity
				.getActionReplacements();
		if (actionReplacements != null) {
			for (ActionReplacementEntity actionReplacement : actionReplacements) {
				actionReplacement.setServer(entity);
				List<ReplacementEntity> replacements = actionReplacement
						.getReplacements();
				if (replacements != null) {
					for (ReplacementEntity replacementEntity : replacements) {
						replacementEntity
								.setActionReplacement(actionReplacement);
					}
				}
			}
		}
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("upload/{id}")
	public Integer uploadFile(@PathParam("id") final Integer serverId,
			@FormDataParam("file") InputStream stream,
			@FormDataParam("file") FormDataContentDisposition contentDisposition,
			@Auth Subject subject) {
		ServerEntity server = getSelector(subject).withId(serverId).find();

		BinaryDataEntity binaryData = new BinaryDataEntity();
		try {
			binaryData.setFilename(contentDisposition.getFileName());
			byte[] bytes = IOUtils.toByteArray(stream);
			binaryData.setSize(bytes.length);
			binaryData.setData(bytes);
			binaryData.setContentType(URLConnection.guessContentTypeFromName(
					contentDisposition.getFileName()));
			server.setIcon(binaryData);
			binaryData = new BinaryDataDao().save(binaryData);
		} catch (IOException e) {
			throw new BadArgumentException(
					"Could not read image: " + e.getMessage());
		}

		return binaryData.getId();
	}

	@Override
	protected void validate(Server dto, ServerEntity entity) {
		super.validate(dto, entity);
		if (entity.getName().contains("/")) {
			throw new BadArgumentException("Invalid characters in name");
		}
	}

	@GET
	@Path("/{id}/deployed")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Version> getDeployedVersions(@PathParam("id") Integer id) {
		ServerEntity server = new ServerDao().findById(id);
		List<VersionEntity> versions = deploymentCalculator
				.getDeployedVersions(server);
		return getMapper().mapAsList(versions, Version.class);
	}

	@GET
	@Path("/{id}/deployable")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Version> getDeployableVersions(@PathParam("id") Integer id) {
		ServerEntity server = new ServerDao().findById(id);
		List<VersionEntity> versions = deploymentCalculator
				.getDeployableVersions(server);
		return getMapper().mapAsList(versions, Version.class);
	}

	@GET
	@Path("/{name}.json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getJson(@PathParam("name") String serverName) {
		ServerEntity server = new ServerDao().findByName(serverName);
		return Response.ok(new JsonRenderer().render(server),
				MediaType.APPLICATION_JSON_TYPE).build();
	}

	@GET
	@Path("/{name}.xml")
	@Produces(MediaType.APPLICATION_XML)
	public Response getXml(@PathParam("name") String serverName) {
		ServerEntity server = new ServerDao().findByName(serverName);
		return Response.ok(new XmlRenderer(servletRequest).render(server),
				MediaType.APPLICATION_XML_TYPE).build();
	}

	@Override
	protected String getCreatePermission() {
		return Permission.ADMIN_SERVERS.name();
	}

	@Override
	protected String getReadPermission() {
		return Permission.SHOW_SERVERS.name();
	}

	@Override
	protected String getUpdatePermission() {
		return Permission.ADMIN_SERVERS.name();
	}

	@Override
	protected String getDeletePermission() {
		return Permission.ADMIN_SERVERS.name();
	}

}
