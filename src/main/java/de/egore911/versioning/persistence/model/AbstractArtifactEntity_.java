package de.egore911.versioning.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AbstractArtifactEntity.class)
public abstract class AbstractArtifactEntity_ extends de.egore911.appframework.persistence.model.IntegerDbObject_ {

	public static volatile SingularAttribute<AbstractArtifactEntity, String> groupId;
	public static volatile SingularAttribute<AbstractArtifactEntity, String> artifactId;
	public static volatile SingularAttribute<AbstractArtifactEntity, String> version;

}

