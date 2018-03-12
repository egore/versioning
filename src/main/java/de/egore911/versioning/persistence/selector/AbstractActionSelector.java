package de.egore911.versioning.persistence.selector;

import java.util.List;

import javax.annotation.Nonnull;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import de.egore911.appframework.persistence.selector.AbstractResourceSelector;
import de.egore911.versioning.persistence.model.AbstractActionEntity;
import de.egore911.versioning.persistence.model.AbstractActionEntity_;
import de.egore911.versioning.persistence.model.ProjectEntity;

public abstract class AbstractActionSelector<T extends AbstractActionEntity> extends AbstractResourceSelector<T> {

	private static final long serialVersionUID = -4914528357142675294L;

	private ProjectEntity project;

	@Override
	protected List<Predicate> generatePredicateList(
			@Nonnull CriteriaBuilder builder, @Nonnull Root<T> from,
			@Nonnull CriteriaQuery<?> criteriaQuery) {
		List<Predicate> predicates = super.generatePredicateList(builder, from, criteriaQuery);

		if (project != null) {
			Join<T, ProjectEntity> fromProject = from.join(AbstractActionEntity_.project);
			predicates.add(fromProject.in(project));
		}

		return predicates;
	}

	public AbstractActionSelector<T> withProject(ProjectEntity projectId) {
		this.project = projectId;
		return this;
	}

}
