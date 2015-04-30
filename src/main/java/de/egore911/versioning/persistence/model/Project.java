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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

import de.egore911.versioning.util.vcs.GitProvider;
import de.egore911.versioning.util.vcs.Provider;
import de.egore911.versioning.util.vcs.SvnProvider;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@Entity
@Table(name = "project", uniqueConstraints = { @UniqueConstraint(name = "project__name", columnNames = { "name" }) })
public class Project extends IntegerDbObject implements Comparable<Project> {

	private static final long serialVersionUID = 526215336577704306L;

	private String name;
	private String description;
	private VcsHost vcsHost;
	private String vcsPath;
	private List<ActionCopy> actionCopies = new ArrayList<>(0);
	private List<ActionExtraction> actionExtractions = new ArrayList<>(0);
	private List<ActionReplacement> actionReplacements = new ArrayList<>(0);
	private List<ActionCheckout> actionCheckouts = new ArrayList<>(0);
	private TagTransformer tagTransformer;
	private List<Server> configuredServers = new ArrayList<>(0);
	private List<Version> versions = new ArrayList<>(0);
	private boolean deleted;

	@Column(nullable = false, length = 255)
	@NotNull
	@Size(min = 1, max = 255)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = true, length = 511)
	@Size(max = 511)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_vcshost", nullable = false)
	@NotNull
	public VcsHost getVcsHost() {
		return vcsHost;
	}

	public void setVcsHost(VcsHost vcsHost) {
		this.vcsHost = vcsHost;
	}

	@Column(nullable = false, length = 255)
	@NotNull
	@Size(min = 1, max = 255)
	public String getVcsPath() {
		return vcsPath;
	}

	public void setVcsPath(String vcsPath) {
		this.vcsPath = vcsPath;
	}

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	public List<ActionCopy> getActionCopies() {
		return actionCopies;
	}

	public void setActionCopies(List<ActionCopy> actionCopies) {
		this.actionCopies = actionCopies;
	}

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	public List<ActionExtraction> getActionExtractions() {
		return actionExtractions;
	}

	public void setActionExtractions(List<ActionExtraction> actionExtractions) {
		this.actionExtractions = actionExtractions;
	}

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	public List<ActionReplacement> getActionReplacements() {
		return actionReplacements;
	}

	public void setActionReplacements(List<ActionReplacement> actionReplacements) {
		this.actionReplacements = actionReplacements;
	}

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	public List<ActionCheckout> getActionCheckouts() {
		return actionCheckouts;
	}

	public void setActionCheckouts(List<ActionCheckout> actionCheckouts) {
		this.actionCheckouts = actionCheckouts;
	}

	@ManyToOne
	@JoinColumn(name = "id_tagtransformer", nullable = true)
	public TagTransformer getTagTransformer() {
		return tagTransformer;
	}

	public void setTagTransformer(TagTransformer tagTransformer) {
		this.tagTransformer = tagTransformer;
	}

	@ManyToMany
	@JoinTable(name = "project_configured_server", joinColumns = @JoinColumn(name = "id_project", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_server", referencedColumnName = "id"))
	@OrderBy("name")
	public List<Server> getConfiguredServers() {
		return configuredServers;
	}

	public void setConfiguredServers(List<Server> configuredServers) {
		this.configuredServers = configuredServers;
	}

	@OneToMany(mappedBy = "project")
	public List<Version> getVersions() {
		return versions;
	}

	public void setVersions(List<Version> versions) {
		this.versions = versions;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public int compareTo(Project o) {
		return getName().compareTo(o.getName());
	}

	@Transient
	public String getCompleteVcsPath() {
		StringBuilder builder = new StringBuilder();
		builder.append(getVcsHost().getUri());
		if (StringUtils.isNotEmpty(getVcsPath())) {
			builder.append(getVcsPath());
		} else {
			builder.append(getName());
		}
		return builder.toString();
	}

	@Transient
	public Provider getProvider() {
		if (getVcsHost() == null || getVcsHost().getVcs() == null) {
			throw new IllegalArgumentException("VCS type not set");
		}
		switch (getVcsHost().getVcs()) {
		case git:
			return new GitProvider();
		case svn:
			return new SvnProvider();
		default:
			throw new RuntimeException("not implemented yet");
		}
	}
}
