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

import java.util.ResourceBundle;

import de.egore911.appframework.ui.exceptions.BadArgumentException;
import de.egore911.versioning.persistence.model.MavenArtifactEntity;
import de.egore911.versioning.persistence.model.MavenRepositoryEntity;
import de.egore911.versioning.util.SessionUtil;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public final class MavenrepositoryUtil {

	private MavenrepositoryUtil() {
	}

	public static void checkDeletable(MavenRepositoryEntity mavenRepository) {
		if (!mavenRepository.getMavenArtifacts().isEmpty()) {
			ResourceBundle bundle = SessionUtil.getBundle();
			StringBuilder projectNames = new StringBuilder();
			for (MavenArtifactEntity mavenArtifact : mavenRepository.getMavenArtifacts()) {
				if (projectNames.length() > 0) {
					projectNames.append(", ");
				}
				projectNames.append(mavenArtifact.getGroupId());
				projectNames.append(':');
				projectNames.append(mavenArtifact.getArtifactId());
			}
			throw new BadArgumentException(bundle.getString("mavenrepository_delete_not_possible_used_by_artifacts") + ": (" + projectNames.toString() + ")");
		}
	}

}
