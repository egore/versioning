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
public class ActionExtractionEntity extends AbstractRemoteActionEntity {

	private static final long serialVersionUID = -6450765572706399548L;

	private List<ExtractionEntity> extractions = new ArrayList<>(0);

	@OneToMany(mappedBy = "actionExtraction", cascade = CascadeType.ALL)
	@OrderBy("source,destination")
	public List<ExtractionEntity> getExtractions() {
		return extractions;
	}

	public void setExtractions(List<ExtractionEntity> extractions) {
		this.extractions = extractions;
	}

	@Override
	@OneToOne(mappedBy = "actionExtraction", cascade = CascadeType.ALL)
	public MavenArtifactEntity getMavenArtifact() {
		return super.getMavenArtifact();
	}

	@Override
	@OneToOne(mappedBy = "actionExtraction", cascade = CascadeType.ALL)
	public SpacerUrlEntity getSpacerUrl() {
		return super.getSpacerUrl();
	}
}
