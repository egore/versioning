package de.egore911.versioning.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ActionExtraction.class)
public abstract class ActionExtraction_ extends de.egore911.versioning.persistence.model.AbstractRemoteAction_ {

	public static volatile SingularAttribute<ActionExtraction, SpacerUrl> spacerUrl;
	public static volatile SingularAttribute<ActionExtraction, MavenArtifact> mavenArtifact;
	public static volatile ListAttribute<ActionExtraction, Extraction> extractions;

}

