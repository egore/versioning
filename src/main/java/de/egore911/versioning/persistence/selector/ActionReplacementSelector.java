package de.egore911.versioning.persistence.selector;

import java.util.List;

import javax.annotation.Nonnull;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import de.egore911.versioning.persistence.model.ActionReplacementEntity;
import de.egore911.versioning.persistence.model.ActionReplacementEntity_;
import de.egore911.versioning.persistence.model.ServerEntity_;

public class ActionReplacementSelector extends AbstractActionSelector<ActionReplacementEntity> {

	private static final long serialVersionUID = -9167158811784544517L;

	private String search;

	@Override
	protected List<Predicate> generatePredicateList(@Nonnull CriteriaBuilder builder,
			@Nonnull Root<ActionReplacementEntity> from, @Nonnull CriteriaQuery<?> criteriaQuery) {
		List<Predicate> predicates = super.generatePredicateList(builder, from, criteriaQuery);

		if (StringUtils.isNotEmpty(search)) {
			predicates.add(
					builder.like(from.get(ActionReplacementEntity_.server).get(ServerEntity_.name), '%' + search + '%')
			);
		}

		return predicates;
	}

	@Override
	public ActionReplacementSelector withSearch(String search) {
		this.search = search;
		return this;
	}

	@Override
	protected Class<ActionReplacementEntity> getEntityClass() {
		return ActionReplacementEntity.class;
	}
}
