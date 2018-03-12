package de.egore911.versioning.persistence.selector;

import java.util.List;

import javax.annotation.Nonnull;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import de.egore911.versioning.persistence.model.ActionExtractionEntity;
import de.egore911.versioning.persistence.model.ActionExtractionEntity_;
import de.egore911.versioning.persistence.model.ExtractionEntity_;
import de.egore911.versioning.persistence.model.MavenArtifactEntity_;
import de.egore911.versioning.persistence.model.SpacerUrlEntity_;

public class ActionExtractionSelector extends AbstractActionSelector<ActionExtractionEntity> {

	private static final long serialVersionUID = 492805097994359469L;

	private String search;

	@Override
	protected List<Predicate> generatePredicateList(@Nonnull CriteriaBuilder builder,
			@Nonnull Root<ActionExtractionEntity> from, @Nonnull CriteriaQuery<?> criteriaQuery) {
		List<Predicate> predicates = super.generatePredicateList(builder, from, criteriaQuery);

		if (StringUtils.isNotEmpty(search)) {
			criteriaQuery.distinct(true);
			predicates.add(builder.or(
					builder.like(from.join(ActionExtractionEntity_.extractions).get(ExtractionEntity_.source), '%' + search + '%'),
					builder.like(from.join(ActionExtractionEntity_.extractions).get(ExtractionEntity_.destination), '%' + search + '%'),
					builder.like(from.get(ActionExtractionEntity_.spacerUrl).get(SpacerUrlEntity_.url), '%' + search + '%'),
					builder.like(from.get(ActionExtractionEntity_.mavenArtifact).get(MavenArtifactEntity_.artifactId), '%' + search + '%'),
					builder.like(from.get(ActionExtractionEntity_.mavenArtifact).get(MavenArtifactEntity_.groupId), '%' + search + '%'))
			);
		}

		return predicates;
	}

	@Override
	public ActionExtractionSelector withSearch(String search) {
		this.search = search;
		return this;
	}

	@Override
	protected Class<ActionExtractionEntity> getEntityClass() {
		return ActionExtractionEntity.class;
	}
}
