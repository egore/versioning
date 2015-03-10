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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@Entity
@Table(name = "maven_repository", uniqueConstraints = { @UniqueConstraint(name = "mavenrepository__name", columnNames = { "name" }) })
public class MavenRepository extends IntegerDbObject {

	private static final long serialVersionUID = 293924997823969971L;

	private String name;
	private String baseUrl;
	private List<MavenArtifact> mavenArtifacts = new ArrayList<>(0);

	@Column(nullable = false, length = 255)
	@NotNull
	@Size(max = 255)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = false, length = 511)
	@NotNull
	@Size(max = 511)
	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	@OneToMany(mappedBy = "mavenRepository")
	public List<MavenArtifact> getMavenArtifacts() {
		return mavenArtifacts;
	}

	public void setMavenArtifacts(List<MavenArtifact> mavenArtifacts) {
		this.mavenArtifacts = mavenArtifacts;
	}

}
