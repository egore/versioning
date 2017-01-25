package de.egore911.versioning.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MavenRepositoryEntity.class)
public abstract class MavenRepositoryEntity_ extends de.egore911.appframework.persistence.model.IntegerDbObject_ {

	public static volatile SingularAttribute<MavenRepositoryEntity, String> baseUrl;
	public static volatile ListAttribute<MavenRepositoryEntity, MavenArtifactEntity> mavenArtifacts;
	public static volatile SingularAttribute<MavenRepositoryEntity, String> name;

}

