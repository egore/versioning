package de.egore911.versioning.persistence.selector;

import java.util.List;

import javax.annotation.Nonnull;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import de.egore911.versioning.persistence.model.ActionCheckoutEntity;
import de.egore911.versioning.persistence.model.ActionCheckoutEntity_;

public class ActionCheckoutSelector extends AbstractActionSelector<ActionCheckoutEntity> {

	private static final long serialVersionUID = -9167158811784544517L;

	private String search;

	@Override
	protected List<Predicate> generatePredicateList(@Nonnull CriteriaBuilder builder,
			@Nonnull Root<ActionCheckoutEntity> from, @Nonnull CriteriaQuery<?> criteriaQuery) {
		List<Predicate> predicates = super.generatePredicateList(builder, from, criteriaQuery);

		if (StringUtils.isNotEmpty(search)) {
			predicates.add(
					builder.like(from.get(ActionCheckoutEntity_.targetPath), '%' + search + '%')
			);
		}

		return predicates;
	}

	@Override
	public ActionCheckoutSelector withSearch(String search) {
		this.search = search;
		return this;
	}

	@Override
	protected Class<ActionCheckoutEntity> getEntityClass() {
		return ActionCheckoutEntity.class;
	}
}
