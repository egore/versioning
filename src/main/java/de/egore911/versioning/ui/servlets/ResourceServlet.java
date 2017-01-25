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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import de.egore911.versioning.persistence.dao.BinaryDataDao;
import de.egore911.versioning.persistence.model.BinaryDataEntity;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@WebServlet(name = "ResourceServlet", urlPatterns = "/service/resources/server/*")
public class ResourceServlet extends HttpServlet {

	private static final long serialVersionUID = 498864582614855618L;

	private static final Pattern PATTERN_SERVERICON = Pattern
			.compile(".*/resources/server/([\\d+])$");

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Matcher matcher = PATTERN_SERVERICON.matcher(req.getRequestURI());
		if (matcher.matches()) {
			Integer iconId = Integer.valueOf(matcher.group(1));
			BinaryDataDao binaryDataDao = new BinaryDataDao();
			BinaryDataEntity binaryData = binaryDataDao.findById(iconId);
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
