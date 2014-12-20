package de.egore911.versioning.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ActionReplacement.class)
public abstract class ActionReplacement_ extends de.egore911.versioning.persistence.model.IntegerDbObject_ {

	public static volatile SingularAttribute<ActionReplacement, Server> server;
	public static volatile ListAttribute<ActionReplacement, Replacementfile> replacementFiles;
	public static volatile ListAttribute<ActionReplacement, Wildcard> wildcards;
	public static volatile SingularAttribute<ActionReplacement, Project> project;
	public static volatile ListAttribute<ActionReplacement, Replacement> replacements;

}

