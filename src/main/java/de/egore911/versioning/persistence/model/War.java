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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@Entity
@Table(name = "action_war")
public class War extends IntegerDbObject {

	private static final long serialVersionUID = -6348284172396876137L;

	private Project project;
	private MavenArtifact mavenArtifact;
	private SpacerUrl spacerUrl;

	@OneToOne(optional = false)
	@JoinColumn(name = "id_project", nullable = false)
	@NotNull
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@OneToOne(mappedBy = "war", cascade = CascadeType.ALL)
	public MavenArtifact getMavenArtifact() {
		return mavenArtifact;
	}

	public void setMavenArtifact(MavenArtifact mavenArtifact) {
		this.mavenArtifact = mavenArtifact;
	}

	@OneToOne(mappedBy = "war", cascade = CascadeType.ALL)
	public SpacerUrl getSpacerUrl() {
		return spacerUrl;
	}

	public void setSpacerUrl(SpacerUrl spacerUrl) {
		this.spacerUrl = spacerUrl;
	}

}
