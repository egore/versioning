package de.egore911.versioning.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ActionExtractionEntity.class)
public abstract class ActionExtractionEntity_ extends de.egore911.versioning.persistence.model.AbstractActionEntity_ {

	public static volatile SingularAttribute<ActionExtractionEntity, SpacerUrlEntity> spacerUrl;
	public static volatile ListAttribute<ActionExtractionEntity, ExtractionEntity> extractions;
	public static volatile SingularAttribute<ActionExtractionEntity, MavenArtifactEntity> mavenArtifact;

}

