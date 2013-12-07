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
					writer.println("<server>");
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
						if (project.getWar() != null) {
							writer.println("			<war>");
							String transformedVcsTag = version
									.getTransformedVcsTag();
							if (project.getWar().getMavenArtifact() != null) {

								MavenArtifact mavenArtifact = project.getWar()
										.getMavenArtifact();
								if (project.getMavenRepository() == null) {
									log.error(
											"Found maven artifact {}:{}:{} without maven repository, skipping!",
											mavenArtifact.getGroupId(),
											mavenArtifact.getArtifactId(),
											version.getVcsTag());
									continue;
								}

								String url = urlUtil.concatenateUrlWithSlashes(
										project.getMavenRepository()
												.getBaseUrl(),
										mavenArtifact.getGroupId().replace('.',
												'/'), mavenArtifact
												.getArtifactId(),
										transformedVcsTag,
										mavenArtifact.getArtifactId() + "-"
												+ transformedVcsTag + ".war");

								writer.print("				<url>");
								writer.print(url);
								writer.println("</url>");
							} else {
								writer.print("				<url>");
								writer.print(project
										.getWar()
										.getSpacerUrl()
										.getUrl()
										.replace("[VERSION]", transformedVcsTag));
								writer.println("</url>");
							}
							writer.print("				<target>");
							writer.print(urlUtil.concatenateUrlWithSlashes(
									server.getTargetdir(), project.getWar()
											.getTargetPath()));
							writer.println("</target>");
							writer.println("			</war>");
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
}
