package de.egore911.versioning.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ActionCopyEntity.class)
public abstract class ActionCopyEntity_ extends de.egore911.versioning.persistence.model.AbstractActionEntity_ {

	public static volatile SingularAttribute<ActionCopyEntity, SpacerUrlEntity> spacerUrl;
	public static volatile SingularAttribute<ActionCopyEntity, String> targetFilename;
	public static volatile SingularAttribute<ActionCopyEntity, String> targetPath;
	public static volatile SingularAttribute<ActionCopyEntity, MavenArtifactEntity> mavenArtifact;

}

