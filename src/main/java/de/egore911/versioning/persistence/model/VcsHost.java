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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@Entity
@Table(name = "vcshost", uniqueConstraints = { @UniqueConstraint(name = "pvcshost__name", columnNames = { "name" }) })
public class VcsHost extends IntegerDbObject {

	private String name;
	private Vcs vcs;
	private String uri;
	private String username;
	private String password;
	private List<Project> projects = new ArrayList<>();

	@Column(nullable = false, length = 255)
	@NotNull
	@Size(max = 255)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	public Vcs getVcs() {
		return vcs;
	}

	public void setVcs(Vcs vcs) {
		this.vcs = vcs;
	}

	@Column(nullable = false, length = 255)
	@NotNull
	@Size(max = 255)
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@Column(length = 255)
	@Size(max = 255)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(length = 63)
	@Size(max = 63)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@OneToMany(mappedBy = "vcsHost")
	@OrderBy("name")
	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	@Override
	public String toString() {
		return "VcsHost [name=" + name + ", vcs=" + vcs + ", uri=" + uri
				+ ", projects=" + projects + ", id=" + getId() + "]";
	}

	@Transient
	public boolean isCredentialsAvailable() {
		return StringUtils.isNotEmpty(getUsername())
				&& StringUtils.isNotEmpty(getPassword());
	}

}
