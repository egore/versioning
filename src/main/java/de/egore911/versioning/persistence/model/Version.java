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
package de.egore911.versioning.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@Entity
@Table(name = "version", uniqueConstraints = { @UniqueConstraint(name = "version__project_vcstag", columnNames = {
		"id_project", "vcs_tag" }) })
public class Version extends IntegerDbObject {

	private static final long serialVersionUID = -5872475957543596131L;

	private Project project;
	private String vcsTag;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_project", nullable = false)
	@NotNull
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Column(nullable = false, length = 50, name = "vcs_tag")
	public String getVcsTag() {
		return vcsTag;
	}

	public void setVcsTag(String vcsTag) {
		this.vcsTag = vcsTag;
	}

	public boolean isNewerThan(Version other) {
		if (other == null) {
			throw new IllegalArgumentException("Given version cannot be null");
		}
		if (getVcsTag() == null) {
			throw new IllegalArgumentException("Own VCS tag cannot be null");
		}
		if (other.getVcsTag() == null) {
			throw new IllegalArgumentException(
					"Given versions VCS tag cannot be null");
		}
		int myDots = StringUtils.countMatches(getVcsTag(), ".");
		int otherDots = StringUtils.countMatches(other.getVcsTag(), ".");
		String[] mySplit = getVcsTag().split("\\.");
		String[] otherSplit = other.getVcsTag().split("\\.");
		int length = Math.min(mySplit.length, otherSplit.length);
		for (int i = 0; i < length; i++) {
			int compare = mySplit[i].compareTo(otherSplit[i]);
			if (compare > 0) {
				return true;
			} else if (compare < 0) {
				return false;
			}
		}

		// Same version
		if (myDots == otherDots) {
			return false;
		} else if (myDots > otherDots) {
			// Controversial: Right now "1.0" is the same as "1.0.0.0.0"
			for (int i = otherDots; i < myDots; i++) {
				if (mySplit[i].equals("0")) {
					continue;
				}
				return true;
			}
			return false;
		} else {
			return false;
		}

	}
}
