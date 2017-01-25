package de.egore911.versioning.ui.rest;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import de.egore911.appframework.ui.dto.VersionInformation;
import de.egore911.appframework.util.VersionExtractor;

@Path("version_info")
@Produces(MediaType.APPLICATION_JSON)
public class VersionInfoService {

	@Context
	private ServletContext servletContext;

	@GET
	public VersionInformation getVersion() {
		return new VersionInformation(
				VersionExtractor.getMavenVersion(servletContext, "de.egore911.versioning", "versioning-web"),
				VersionExtractor.getGitVersion(servletContext, "de.egore911.versioning", "versioning-web"),
				VersionExtractor.getBuildTimestamp(servletContext, "de.egore911.versioning", "versioning-web"));
	}

}
