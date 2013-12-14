package de.egore911.versioning.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ActionReplacement.class)
public abstract class ActionReplacement_ extends de.egore911.versioning.persistence.model.AbstractAction_ {

	public static volatile ListAttribute<ActionReplacement, Replacement> replacements;
	public static volatile ListAttribute<ActionReplacement, Wildcard> wildcards;
	public static volatile ListAttribute<ActionReplacement, Replacementfile> replacementFiles;

}

