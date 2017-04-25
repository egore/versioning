package de.egore911.versioning.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(VerificationEntity.class)
public abstract class VerificationEntity_ extends de.egore911.appframework.persistence.model.IntegerDbObject_ {

	public static volatile SingularAttribute<VerificationEntity, String> groupId;
	public static volatile SingularAttribute<VerificationEntity, String> artifactId;
	public static volatile SingularAttribute<VerificationEntity, String> packaging;
	public static volatile SingularAttribute<VerificationEntity, String> version;

}

