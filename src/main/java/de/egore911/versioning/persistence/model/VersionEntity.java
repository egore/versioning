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
package de.egore911.versioning.persistence.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.BatchSize;

import de.egore911.appframework.persistence.model.IntegerDbObject;
import de.egore911.versioning.util.VersionUtil;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@Entity
@Table(name = "version", uniqueConstraints = { @UniqueConstraint(name = "version__project_vcstag", columnNames = {
		"id_project", "vcs_tag" }) })
public class VersionEntity extends IntegerDbObject {

	private static final long serialVersionUID = -5872475957543596131L;

	private ProjectEntity project;
	private String vcsTag;
	private List<DeploymentEntity> deployments = new ArrayList<>(0);

	@BatchSize(size = 50)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_project", nullable = false)
	@NotNull
	public ProjectEntity getProject() {
		return project;
	}

	public void setProject(ProjectEntity project) {
		this.project = project;
	}

	@Column(nullable = false, length = 50, name = "vcs_tag")
	public String getVcsTag() {
		return vcsTag;
	}

	public void setVcsTag(String vcsTag) {
		this.vcsTag = vcsTag;
	}

	@OneToMany(mappedBy = "version", cascade = CascadeType.ALL)
	@BatchSize(size = 100)
	public List<DeploymentEntity> getDeployments() {
		return deployments;
	}

	public void setDeployments(List<DeploymentEntity> deployments) {
		this.deployments = deployments;
	}

	@Transient
	public String getTransformedVcsTag() {
		TagTransformerEntity tagTransformer = getProject().getTagTransformer();
		if (tagTransformer != null) {
			return getVcsTag().replaceAll(tagTransformer.getSearchPattern(),
					tagTransformer.getReplacementPattern());
		}
		return getVcsTag();
	}

	@Transient
	public boolean isNewerThan(VersionEntity other) {
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
