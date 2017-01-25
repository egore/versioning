package de.egore911.versioning.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ActionReplacementEntity.class)
public abstract class ActionReplacementEntity_ extends de.egore911.appframework.persistence.model.IntegerDbObject_ {

	public static volatile SingularAttribute<ActionReplacementEntity, ServerEntity> server;
	public static volatile ListAttribute<ActionReplacementEntity, String> replacementfiles;
	public static volatile ListAttribute<ActionReplacementEntity, String> wildcards;
	public static volatile SingularAttribute<ActionReplacementEntity, ProjectEntity> project;
	public static volatile ListAttribute<ActionReplacementEntity, ReplacementEntity> replacements;

}

