package de.egore911.versioning.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.joda.time.LocalDateTime;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Deployment.class)
public abstract class Deployment_ extends de.egore911.versioning.persistence.model.IntegerDbObject_ {

	public static volatile SingularAttribute<Deployment, Server> server;
	public static volatile SingularAttribute<Deployment, Version> version;
	public static volatile SingularAttribute<Deployment, LocalDateTime> undeployment;
	public static volatile SingularAttribute<Deployment, LocalDateTime> deployment;

}

