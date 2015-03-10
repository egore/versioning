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
