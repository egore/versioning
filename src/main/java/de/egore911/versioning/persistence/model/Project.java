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
import javax.persistence.JoinTable;
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
@Table(name = "project", uniqueConstraints = { @UniqueConstraint(name = "project__name", columnNames = { "name" }) })
public class Project extends IntegerDbObject implements Comparable<Project> {

	private String name;
	private VcsHost vcsHost;
	private List<Server> configuredServers = new ArrayList<>(0);
	private List<Version> versions = new ArrayList<>(0);

	@Column(nullable = false, length = 255)
	@NotNull
	@Size(min = 1, max = 255)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Override
	public int compareTo(Project o) {
		return getName().compareTo(o.getName());
	}

}
