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
import java.util.jar.Manifest;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@ManagedBean(name = "applicationBean")
@ApplicationScoped
public class ApplicationBean {

	private String versionNumber;

	public String getVersionNumber() {
		if (versionNumber == null) {
			Manifest manifest = new Manifest();
			ServletContext servletContext = (ServletContext) FacesContext
					.getCurrentInstance().getExternalContext().getContext();
			try {
				manifest.read(servletContext
						.getResourceAsStream("/META-INF/MANIFEST.MF"));
				versionNumber = manifest.getMainAttributes().getValue(
						"Implementation-Version");
			} catch (IOException e) {
				versionNumber = "development";
			}
		}
		return versionNumber;
	}
}
