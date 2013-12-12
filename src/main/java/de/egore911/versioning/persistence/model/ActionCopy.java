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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@Entity
@Table(name = "action_copy")
public class ActionCopy extends AbstractAction {

	private static final long serialVersionUID = -6348284172396876137L;

	private String targetPath;
	private String targetFilename;

	@Column(length = 511, name = "target_path")
	@Size(max = 511)
	public String getTargetPath() {
		return targetPath;
	}

	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}

	@Column(length = 254, name = "target_filename")
	@Size(max = 254)
	public String getTargetFilename() {
		return targetFilename;
	}

	public void setTargetFilename(String targetFilename) {
		this.targetFilename = targetFilename;
	}

	@Override
	@OneToOne(mappedBy = "actionCopy", cascade = CascadeType.ALL)
	public MavenArtifact getMavenArtifact() {
		return super.getMavenArtifact();
	}

	@Override
	@OneToOne(mappedBy = "actionCopy", cascade = CascadeType.ALL)
	public SpacerUrl getSpacerUrl() {
		return super.getSpacerUrl();
	}

}
