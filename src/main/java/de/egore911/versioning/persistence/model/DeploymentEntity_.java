package de.egore911.versioning.persistence.model;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DeploymentEntity.class)
public abstract class DeploymentEntity_ extends de.egore911.appframework.persistence.model.IntegerDbObject_ {

	public static volatile SingularAttribute<DeploymentEntity, ServerEntity> server;
	public static volatile SingularAttribute<DeploymentEntity, VersionEntity> version;
	public static volatile SingularAttribute<DeploymentEntity, LocalDateTime> undeployment;
	public static volatile SingularAttribute<DeploymentEntity, LocalDateTime> deployment;

}

