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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import de.egore911.versioning.util.VersionUtil;

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
	private List<Deployment> deployments = new ArrayList<>(0);

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

	@OneToMany(mappedBy = "version")
	public List<Deployment> getDeployments() {
		return deployments;
	}

	public void setDeployments(List<Deployment> deployments) {
		this.deployments = deployments;
	}

	@Transient
	public String getTransformedVcsTag() {
		TagTransformer tagTransformer = getProject().getTagTransformer();
		if (tagTransformer != null) {
			return getVcsTag().replaceAll(tagTransformer.getSearchPattern(),
					tagTransformer.getReplacementPattern());
		}
		return getVcsTag();
	}

	@Transient
	public boolean isNewerThan(Version other) {
		if (other == null) {
			throw new IllegalArgumentException("Given version cannot be null");
		}
		String transformedVcsTag = getTransformedVcsTag();
		if (transformedVcsTag == null) {
			throw new IllegalArgumentException("Own VCS tag cannot be null");
		}
		String otherTransformedVcsTag = other.getTransformedVcsTag();
		if (otherTransformedVcsTag == null) {
			throw new IllegalArgumentException(
					"Given versions VCS tag cannot be null");
		}
		try {
			return VersionUtil.isNewerThan(transformedVcsTag,
					otherTransformedVcsTag);
		} catch (RuntimeException e) {
			throw new RuntimeException(getProject().getName() + " "
					+ getVcsTag() + " is invalid, check tag transformer: "
					+ e.getMessage(), e);
		}
	}
}
