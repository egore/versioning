package de.egore911.versioning.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MavenRepository.class)
public abstract class MavenRepository_ extends de.egore911.versioning.persistence.model.IntegerDbObject_ {

	public static volatile SingularAttribute<MavenRepository, String> baseUrl;
	public static volatile ListAttribute<MavenRepository, MavenArtifact> mavenArtifacts;
	public static volatile SingularAttribute<MavenRepository, String> name;

}

