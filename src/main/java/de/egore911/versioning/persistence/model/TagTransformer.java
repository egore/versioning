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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@Entity
@Table(name = "tag_transformer")
public class TagTransformer extends IntegerDbObject {

	private static final long serialVersionUID = 6027345356515179670L;

	private String name;
	private String searchPattern;
	private String replacementPattern;
	private List<Project> projects = new ArrayList<>(0);

	@Column(nullable = false, length = 255)
	@NotNull
	@Size(min = 1, max = 255)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = false, length = 511)
	@NotNull
	@Size(min = 1, max = 511)
	public String getSearchPattern() {
		return searchPattern;
	}

	public void setSearchPattern(String searchPattern) {
		this.searchPattern = searchPattern;
	}

	@Column(nullable = false, length = 511)
	@NotNull
	@Size(min = 1, max = 511)
	public String getReplacementPattern() {
		return replacementPattern;
	}

	public void setReplacementPattern(String replacementPattern) {
		this.replacementPattern = replacementPattern;
	}

	@OneToMany(mappedBy = "tagTransformer")
	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

}
