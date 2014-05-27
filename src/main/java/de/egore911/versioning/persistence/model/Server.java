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

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@Entity
@Table(name = "server", uniqueConstraints = { @UniqueConstraint(name = "project__name", columnNames = { "name" }) })
public class Server extends IntegerDbObject {

	private static final long serialVersionUID = -1152147569504508181L;

	private String name;
	private String description;
	private String targetdir;
	private VcsHost vcsHost;
	private String vcsPath;
	private String targetPath;
	private List<Variable> variables = new ArrayList<>(0);
	private List<Project> configuredProjects = new ArrayList<>(0);
	private List<ActionReplacement> actionReplacements = new ArrayList<>(0);
	private BinaryData icon;
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
	public VcsHost getVcsHost() {
		return vcsHost;
	}

	public void setVcsHost(VcsHost vcsHost) {
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

	@OneToMany(mappedBy = "server", cascade = CascadeType.ALL)
	public List<Variable> getVariables() {
		return variables;
	}

	public void setVariables(List<Variable> variables) {
		this.variables = variables;
	}

	@ManyToMany(mappedBy = "configuredServers", cascade = CascadeType.ALL)
	@OrderBy("name")
	public List<Project> getConfiguredProjects() {
		return configuredProjects;
	}

	public void setConfiguredProjects(List<Project> configuredProjects) {
		this.configuredProjects = configuredProjects;
	}

	@OneToMany(mappedBy = "server", cascade = CascadeType.ALL)
	public List<ActionReplacement> getActionReplacements() {
		return actionReplacements;
	}

	public void setActionReplacements(List<ActionReplacement> actionReplacements) {
		this.actionReplacements = actionReplacements;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_binary_data_icon")
	public BinaryData getIcon() {
		return icon;
	}

	public void setIcon(BinaryData icon) {
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
