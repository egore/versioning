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

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.egore911.versioning.ui.beans.ApplicationBean;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class InfoService extends HttpServlet {

	private static final long serialVersionUID = 102133277451065336L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		try (PrintWriter writer = resp.getWriter()) {
			ApplicationBean applicationBean = (ApplicationBean) getServletContext()
					.getAttribute("applicationBean");
			String versionNumber;
			if (applicationBean == null) {
				// Not instantiated by JSF yet, perform operation uncached
				versionNumber = ApplicationBean
						.readVersion(getServletContext());
			} else {
				versionNumber = applicationBean.getVersionNumber();
			}
			writer.println(versionNumber);
		}
	}
}
