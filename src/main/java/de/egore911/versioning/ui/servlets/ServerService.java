/*
 * Copyright 2013  Christoph Brill <egore911@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
			.compile(".*/server/([^/]+)\\.(xml|json)$");

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
					if ("xml".equals(matcher.group(2))) {
						renderXml(req, resp, writer, server);
					} else if ("json".equals(matcher.group(2))) {
						renderJson(req, resp, writer, server);
					} else {
						resp.sendError(HttpServletResponse.SC_NOT_FOUND,
								"Not-Found: response type");
					}
				} else {
					resp.sendError(HttpServletResponse.SC_NOT_FOUND,
							"Not-Found: " + serverName);
				}
			} else {

				List<Server> servers = new ServerDao().findAll();
				for (Server server : servers) {
					writer.println(server.getName() + ".xml");
					writer.println(server.getName() + ".json");
				}

			}
		}
	}

	private void renderJson(HttpServletRequest req, HttpServletResponse resp,
			PrintWriter writer, Server server) {
		writer.print(new JsonRenderer().render(req, resp, server));
	}

	private void renderXml(HttpServletRequest req, HttpServletResponse resp,
			PrintWriter writer, Server server) {
		writer.print(new XmlRenderer().render(req, resp, server));
	}

}
