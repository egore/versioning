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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import de.egore911.appframework.persistence.model.BinaryDataEntity;
import de.egore911.appframework.persistence.model.IntegerDbObject;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@Entity
@Table(name = "server", uniqueConstraints = @UniqueConstraint(name = "project__name", columnNames = "name"))
public class ServerEntity extends IntegerDbObject {

	private static final long serialVersionUID = -1152147569504508181L;

	private String name;
	private String description;
	private String targetdir;
	private VcsHostEntity vcsHost;
	private String vcsPath;
	private String targetPath;
	private List<VariableEntity> variables = new ArrayList<>(0);
	private List<ProjectEntity> configuredProjects = new ArrayList<>(0);
	private List<ActionReplacementEntity> actionReplacements = new ArrayList<>(0);
	private BinaryDataEntity icon;
	private Integer iconId;

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

	/**
	 * @return specifies the path on the server where to put the deployments
	 *         (e.g. /var/lib/tomcat/webapps)
	 */
	@Column(nullable = true, length = 1023)
	@Size(max = 1023)
	public String getTargetdir() {
		return targetdir;
	}

	public void setTargetdir(String targetdir) {
		this.targetdir = targetdir;
	}

	@ManyToOne
	@JoinColumn(name = "id_vcshost")
	public VcsHostEntity getVcsHost() {
		return vcsHost;
	}

	public void setVcsHost(VcsHostEntity vcsHost) {
		this.vcsHost = vcsHost;
	}

	@Column(length = 255)
	@Size(max = 255)
	public String getVcsPath() {
		return vcsPath;
	}

	public void setVcsPath(String vcsPath) {
		this.vcsPath = vcsPath;
	}

	/**
	 * @return path of the servers configuration checkout directory relative to {@link #getTargetdir()}
	 */
	@Column(length = 511, name = "target_path")
	@Size(max = 511)
	public String getTargetPath() {
		return targetPath;
	}

	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}

	@OneToMany(mappedBy = "server", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<VariableEntity> getVariables() {
		return variables;
	}

	public void setVariables(List<VariableEntity> variables) {
		this.variables = variables;
	}

	@ManyToMany(mappedBy = "configuredServers", cascade = CascadeType.ALL)
	@OrderBy("name")
	public List<ProjectEntity> getConfiguredProjects() {
		return configuredProjects;
	}

	public void setConfiguredProjects(List<ProjectEntity> configuredProjects) {
		this.configuredProjects = configuredProjects;
	}

	@OneToMany(mappedBy = "server", cascade = CascadeType.ALL)
	public List<ActionReplacementEntity> getActionReplacements() {
		return actionReplacements;
	}

	public void setActionReplacements(List<ActionReplacementEntity> actionReplacements) {
		this.actionReplacements = actionReplacements;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_binary_data_icon")
	public BinaryDataEntity getIcon() {
		return icon;
	}

	public void setIcon(BinaryDataEntity icon) {
		this.icon = icon;
	}

	@Column(name = "id_binary_data_icon", updatable = false, insertable = false)
	public Integer getIconId() {
		return iconId;
	}

	public void setIconId(Integer iconId) {
		this.iconId = iconId;
	}

}
