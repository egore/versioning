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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import de.egore911.versioning.persistence.dao.BinaryDataDao;
import de.egore911.versioning.persistence.model.BinaryData;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class ResourceServlet extends HttpServlet {

	private static final long serialVersionUID = 498864582614855618L;

	private static final Pattern PATTERN_SERVERICON = Pattern
			.compile(".*/resources/server/([\\d+])$");

	@Inject
	private BinaryDataDao binaryDataDao;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Matcher matcher = PATTERN_SERVERICON.matcher(req.getRequestURI());
		if (matcher.matches()) {
			Integer iconId = Integer.valueOf(matcher.group(1));
			BinaryData binaryData = binaryDataDao.findById(iconId);
			if (binaryData != null) {
				resp.setContentLength((int) binaryData.getSize());
				resp.setContentType(binaryData.getContentType());
				resp.setHeader("Content-Disposition", "attachment; filename=\""
						+ binaryData.getFilename() + "\"");
				try (ServletOutputStream out = resp.getOutputStream()) {
					IOUtils.write(binaryData.getData(), out);
				}
			} else {
				super.doGet(req, resp);
			}
		}
	}
}
