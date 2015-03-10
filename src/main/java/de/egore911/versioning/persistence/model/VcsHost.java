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

	private static final long serialVersionUID = 143813924992020021L;

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
