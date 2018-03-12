package de.egore911.versioning.persistence.selector;

import java.util.List;

import javax.annotation.Nonnull;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import de.egore911.versioning.persistence.model.ActionCopyEntity;
import de.egore911.versioning.persistence.model.ActionCopyEntity_;
import de.egore911.versioning.persistence.model.MavenArtifactEntity_;
import de.egore911.versioning.persistence.model.SpacerUrlEntity_;

public class ActionCopySelector extends AbstractActionSelector<ActionCopyEntity> {

	private static final long serialVersionUID = 7369048491635670534L;

	private String search;

	@Override
	protected List<Predicate> generatePredicateList(@Nonnull CriteriaBuilder builder,
			@Nonnull Root<ActionCopyEntity> from, @Nonnull CriteriaQuery<?> criteriaQuery) {
		List<Predicate> predicates = super.generatePredicateList(builder, from, criteriaQuery);

		if (StringUtils.isNotEmpty(search)) {
			predicates.add(builder.or(
					builder.like(from.get(ActionCopyEntity_.targetFilename), '%' + search + '%'),
					builder.like(from.get(ActionCopyEntity_.targetPath), '%' + search + '%'),
					builder.like(from.get(ActionCopyEntity_.spacerUrl).get(SpacerUrlEntity_.url), '%' + search + '%'),
					builder.like(from.get(ActionCopyEntity_.mavenArtifact).get(MavenArtifactEntity_.artifactId), '%' + search + '%'),
					builder.like(from.get(ActionCopyEntity_.mavenArtifact).get(MavenArtifactEntity_.groupId), '%' + search + '%'))
			);
		}

		return predicates;
	}

	@Override
	public ActionCopySelector withSearch(String search) {
		this.search = search;
		return this;
	}

	@Override
	protected Class<ActionCopyEntity> getEntityClass() {
		return ActionCopyEntity.class;
	}

}
