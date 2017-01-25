package de.egore911.versioning.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MavenArtifactEntity.class)
public abstract class MavenArtifactEntity_ extends de.egore911.appframework.persistence.model.IntegerDbObject_ {

	public static volatile SingularAttribute<MavenArtifactEntity, ActionCopyEntity> actionCopy;
	public static volatile SingularAttribute<MavenArtifactEntity, String> groupId;
	public static volatile SingularAttribute<MavenArtifactEntity, ActionExtractionEntity> actionExtraction;
	public static volatile SingularAttribute<MavenArtifactEntity, String> artifactId;
	public static volatile SingularAttribute<MavenArtifactEntity, String> packaging;
	public static volatile SingularAttribute<MavenArtifactEntity, MavenRepositoryEntity> mavenRepository;

}

