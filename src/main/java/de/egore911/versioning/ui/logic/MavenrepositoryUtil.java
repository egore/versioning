/*
 * Copyright 2016  Christoph Brill <egore911@gmail.com>
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
package de.egore911.versioning.ui.logic;

import de.egore911.versioning.persistence.model.MavenArtifact;
import de.egore911.versioning.persistence.model.MavenRepository;
import de.egore911.versioning.util.SessionUtil;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.ResourceBundle;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public final class MavenrepositoryUtil {

	private MavenrepositoryUtil() {
	}

	public static boolean isDeletable(MavenRepository mavenRepository) {
		if (!mavenRepository.getMavenArtifacts().isEmpty()) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ResourceBundle bundle = SessionUtil.getBundle();
			StringBuilder projectNames = new StringBuilder();
			for (MavenArtifact mavenArtifact : mavenRepository.getMavenArtifacts()) {
				if (projectNames.length() > 0) {
					projectNames.append(", ");
				}
				projectNames.append(mavenArtifact.getGroupId());
				projectNames.append(':');
				projectNames.append(mavenArtifact.getArtifactId());
			}
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					bundle.getString("mavenrepository_delete_not_possible_used_by_artifacts"),
					projectNames.toString());
			facesContext.addMessage("main:table", message);
			return false;
		}
		return true;
	}

}
