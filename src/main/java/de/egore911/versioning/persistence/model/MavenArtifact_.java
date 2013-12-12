package de.egore911.versioning.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MavenArtifact.class)
public abstract class MavenArtifact_ extends de.egore911.versioning.persistence.model.IntegerDbObject_ {

	public static volatile SingularAttribute<MavenArtifact, String> groupId;
	public static volatile SingularAttribute<MavenArtifact, String> packaging;
	public static volatile SingularAttribute<MavenArtifact, ActionExtraction> actionExtraction;
	public static volatile SingularAttribute<MavenArtifact, ActionCopy> actionCopy;
	public static volatile SingularAttribute<MavenArtifact, String> artifactId;
	public static volatile SingularAttribute<MavenArtifact, MavenRepository> mavenRepository;

}

