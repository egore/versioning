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

package de.egore911.versioning.persistence.selector;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import de.egore911.appframework.persistence.selector.AbstractResourceSelector;
import de.egore911.versioning.persistence.model.VerificationEntity;
import de.egore911.versioning.persistence.model.VerificationEntity_;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class VerificationSelector
		extends AbstractResourceSelector<VerificationEntity> {

	private static final long serialVersionUID = -2360869703523135671L;

	private String search;
	private String groupId;

	private String artifactId;

	private String version;

	private String packaging;

	@Override
	protected Class<VerificationEntity> getEntityClass() {
		return VerificationEntity.class;
	}

	@Override
	protected List<Predicate> generatePredicateList(CriteriaBuilder builder,
			Root<VerificationEntity> from, CriteriaQuery<?> criteriaQuery) {
		List<Predicate> predicates = super.generatePredicateList(builder, from,
				criteriaQuery);

		if (StringUtils.isNotEmpty(search)) {
			String likePattern = '%' + search + '%';
			predicates.add(
					builder.or(
							builder.like(from.get(VerificationEntity_.groupId), likePattern),
							builder.like(from.get(VerificationEntity_.artifactId), likePattern),
							builder.like(from.get(VerificationEntity_.version), likePattern)
					)
			);
		}

		if (StringUtils.isNotEmpty(groupId)) {
			predicates.add(builder.or(
					builder.equal(from.get(VerificationEntity_.groupId), groupId),
					builder.equal(from.get(VerificationEntity_.groupId), "*")
			));
		}

		if (StringUtils.isNotEmpty(artifactId)) {
			predicates.add(builder.or(
					builder.equal(from.get(VerificationEntity_.artifactId), artifactId),
					builder.equal(from.get(VerificationEntity_.artifactId), "*")
			));
		}

		if (StringUtils.isNotEmpty(version)) {
			predicates.add(builder.or(
					builder.equal(from.get(VerificationEntity_.version), version),
					builder.equal(from.get(VerificationEntity_.version), "*")
			));
		}

		if (StringUtils.isNotEmpty(packaging)) {
			predicates.add(builder.or(
					builder.equal(from.get(VerificationEntity_.packaging), packaging),
					builder.equal(from.get(VerificationEntity_.packaging), "*")
			));
		}

		return predicates;
	}

	@Override
	public AbstractResourceSelector<VerificationEntity> withSearch(
			String search) {
		this.search = search;
		return this;
	}

	public VerificationSelector withGroupId(String groupId) {
		this.groupId = groupId;
		return this;
	}

	public VerificationSelector withArtifactId(String artifactId) {
		this.artifactId = artifactId;
		return this;
	}

	public VerificationSelector withVersion(String version) {
		this.version = version;
		return this;
	}

	public VerificationSelector withPackaging(String packaging) {
		this.packaging = packaging;
		return this;
	}

}
