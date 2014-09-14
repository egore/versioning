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
package de.egore911.versioning.ui.beans;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Manifest;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@Named
@RequestScoped
public class ApplicationBean {

	private String versionNumber;

	public String getVersionNumber() {
		if (versionNumber == null) {
			ServletContext servletContext = (ServletContext) FacesContext
					.getCurrentInstance().getExternalContext().getContext();
			versionNumber = readVersion(servletContext);
		}
		return versionNumber;
	}

	public static String readVersion(ServletContext servletContext) {
		Manifest manifest = new Manifest();
		try (InputStream manifestFile = servletContext
					.getResourceAsStream("/META-INF/MANIFEST.MF")) {
			manifest.read(manifestFile);
			if (manifestFile == null) {
				return "development";
			}
			return manifest.getMainAttributes().getValue(
					"Implementation-Version");
		} catch (IOException e) {
			return "development";
		}
	}
}
