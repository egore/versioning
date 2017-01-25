package de.egore911.versioning.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(VersionEntity.class)
public abstract class VersionEntity_ extends de.egore911.appframework.persistence.model.IntegerDbObject_ {

	public static volatile SingularAttribute<VersionEntity, String> vcsTag;
	public static volatile ListAttribute<VersionEntity, DeploymentEntity> deployments;
	public static volatile SingularAttribute<VersionEntity, ProjectEntity> project;

}

