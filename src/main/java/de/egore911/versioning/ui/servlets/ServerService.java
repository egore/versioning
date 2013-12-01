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

import de.egore911.versioning.persistence.dao.ServerDao;
import de.egore911.versioning.persistence.model.Server;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class ServerService extends HttpServlet {

	private static final long serialVersionUID = 838967528375645851L;

	private static final Pattern PATTERN_SERVERNAME = Pattern
			.compile(".*/server/([^/]+)\\.xml$");

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

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
					writer.print("	<id>");
					writer.print(server.getId());
					writer.println("</id>");
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
