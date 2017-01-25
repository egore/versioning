package de.egore911.versioning.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(VariableEntity.class)
public abstract class VariableEntity_ extends de.egore911.appframework.persistence.model.IntegerDbObject_ {

	public static volatile SingularAttribute<VariableEntity, ServerEntity> server;
	public static volatile SingularAttribute<VariableEntity, String> name;
	public static volatile SingularAttribute<VariableEntity, String> value;

}

