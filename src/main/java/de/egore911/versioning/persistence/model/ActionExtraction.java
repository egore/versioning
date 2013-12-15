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
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@Entity
@Table(name = "action_extraction")
public class ActionExtraction extends AbstractRemoteAction {

	private static final long serialVersionUID = -6450765572706399548L;

	private List<Extraction> extractions = new ArrayList<>(0);

	@OneToMany(mappedBy = "actionExtraction", cascade = CascadeType.ALL)
	@OrderBy("source,destination")
	public List<Extraction> getExtractions() {
		return extractions;
	}

	public void setExtractions(List<Extraction> extractions) {
		this.extractions = extractions;
	}

	@Override
	@OneToOne(mappedBy = "actionExtraction", cascade = CascadeType.ALL)
	public MavenArtifact getMavenArtifact() {
		return super.getMavenArtifact();
	}

	@Override
	@OneToOne(mappedBy = "actionExtraction", cascade = CascadeType.ALL)
	public SpacerUrl getSpacerUrl() {
		return super.getSpacerUrl();
	}
}
