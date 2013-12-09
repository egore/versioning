/*
 * Copyright 2013  Christoph Brill <egore911@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.egore911.versioning.ui.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egore911.versioning.persistence.dao.ServerDao;
import de.egore911.versioning.persistence.model.AbstractAction;
import de.egore911.versioning.persistence.model.ActionCopy;
import de.egore911.versioning.persistence.model.ActionExtraction;
import de.egore911.versioning.persistence.model.Extraction;
import de.egore911.versioning.persistence.model.MavenArtifact;
import de.egore911.versioning.persistence.model.Project;
import de.egore911.versioning.persistence.model.Server;
import de.egore911.versioning.persistence.model.Version;
import de.egore911.versioning.ui.logic.DeploymentCalculator;
import de.egore911.versioning.util.UrlUtil;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class ServerService extends HttpServlet {

	private static final long serialVersionUID = 838967528375645851L;

	private static final Logger log = LoggerFactory
			.getLogger(ServerService.class);

	private static final Pattern PATTERN_SERVERNAME = Pattern
			.compile(".*/server/([^/]+)\\.xml$");

	private DeploymentCalculator deploymentCalculator = new DeploymentCalculator();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		UrlUtil urlUtil = new UrlUtil();

		Matcher matcher = PATTERN_SERVERNAME.matcher(req.getRequestURI());
		try (PrintWriter writer = resp.getWriter()) {
			if (matcher.matches()) {
				String serverName = matcher.group(1);
				serverName = URLDecoder.decode(serverName, "UTF-8");
				Server server = new ServerDao().findByName(serverName);
				if (server != null) {
					resp.setContentType("application/xml;charset=UTF-8");
					writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
					writer.print("<server xmlns=\"http://versioning.egore911.de/server/1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://versioning.egore911.de/server/1.0 ");
					writer.print(req.getScheme());
					writer.print("://");
					writer.print(req.getServerName());
					switch (req.getScheme()) {
					case "http":
						if (req.getServerPort() != 80) {
							writer.print(":");
							writer.print(req.getServerPort());
						}
						break;
					case "https":
						if (req.getServerPort() != 443) {
							writer.print(":");
							writer.print(req.getServerPort());
						}
						break;
					default:
						writer.print(":");
						writer.print(req.getServerPort());
					}
					writer.print(req.getServletContext().getContextPath());
					writer.println("/xsd/server-1.0.xsd\">");
					writer.print("	<name>");
					writer.print(server.getName());
					writer.println("</name>");
					if (StringUtils.isNotEmpty(server.getDescription())) {
						writer.print("	<!-- ");
						writer.print(server.getDescription()
								.replace("--", "__"));
						writer.println("-->");
					}
					List<Version> versions = deploymentCalculator
							.getDeployableVersions(server);
					writer.println("	<deployments>");
					for (Version version : versions) {
						writer.print("		<!-- ");
						Project project = version.getProject();
						writer.print(project.getName().replace("--", "__"));
						writer.println("-->");
						writer.println("		<deployment>");
						ActionCopy actionCopy = project.getActionCopy();
						if (actionCopy != null) {
							writer.println("			<copy>");
							String transformedVcsTag = version
									.getTransformedVcsTag();
							if (appendUrl(project, version, actionCopy,
									transformedVcsTag, urlUtil, writer)) {
								writer.print("				<target>");
								writer.print(urlUtil.concatenateUrlWithSlashes(
										server.getTargetdir(),
										actionCopy.getTargetPath()));
								writer.println("</target>");
							}
							writer.println("			</copy>");
						}

						ActionExtraction actionExtraction = project
								.getActionExtraction();
						if (actionExtraction != null) {
							writer.println("			<extract>");
							String transformedVcsTag = version
									.getTransformedVcsTag();
							if (appendUrl(project, version, actionExtraction,
									transformedVcsTag, urlUtil, writer)) {
								writer.println("				<extractions>");
								for (Extraction extraction : actionExtraction
										.getExtractions()) {
									writer.println("					<extraction>");
									writer.print("						<source>");
									writer.print(extraction.getSource());
									writer.println("</source>");
									writer.print("						<destination>");
									writer.print(urlUtil.concatenateUrlWithSlashes(
											server.getTargetdir(),
											extraction.getDestination()));
									writer.println("</destination>");
									writer.println("					</extraction>");
								}
								writer.println("				</extractions>");
							}
							writer.println("			</extract>");
						}

						writer.println("		</deployment>");
					}
					writer.println("	</deployments>");
					writer.println("</server>");
				} else {
					// TODO send proper 404
					writer.println("Not-Found: ");
					writer.println(serverName);
				}
			} else {

				List<Server> servers = new ServerDao().findAll();
				for (Server server : servers) {
					writer.println(server.getName() + ".xml");
				}

			}
		}
	}

	private static boolean appendUrl(Project project, Version version,
			AbstractAction action, String transformedVcsTag, UrlUtil urlUtil,
			PrintWriter writer) {
		if (action.getMavenArtifact() != null) {

			MavenArtifact mavenArtifact = action.getMavenArtifact();
			if (project.getMavenRepository() == null) {
				log.error(
						"Found maven artifact {}:{}:{} without maven repository, skipping!",
						mavenArtifact.getGroupId(),
						mavenArtifact.getArtifactId(), version.getVcsTag());
				return false;
			}

			String packaging = mavenArtifact.getPackaging();
			if (StringUtils.isEmpty(packaging)) {
				packaging = "jar";
			}

			String filename = mavenArtifact.getArtifactId() + "-"
					+ transformedVcsTag + "." + packaging;
			String url = urlUtil.concatenateUrlWithSlashes(project
					.getMavenRepository().getBaseUrl(), mavenArtifact
					.getGroupId().replace('.', '/'), mavenArtifact
					.getArtifactId(), transformedVcsTag, filename);

			writer.print("				<url>");
			writer.print(url);
			writer.println("</url>");
		} else if (action.getSpacerUrl() != null) {
			writer.print("				<url>");
			writer.print(action.getSpacerUrl().getUrl()
					.replace("[VERSION]", transformedVcsTag));
			writer.println("</url>");
		} else {
			log.error(
					"Found neither maven artifact nor spacerUrl in project {}",
					project.getName());
			return false;
		}

		return true;
	}

}
