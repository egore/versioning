package de.egore911.versioning.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ActionCopy.class)
public abstract class ActionCopy_ extends de.egore911.versioning.persistence.model.AbstractAction_ {

	public static volatile SingularAttribute<ActionCopy, String> targetPath;
	public static volatile SingularAttribute<ActionCopy, String> targetFilename;
	public static volatile SingularAttribute<ActionCopy, SpacerUrl> spacerUrl;
	public static volatile SingularAttribute<ActionCopy, MavenArtifact> mavenArtifact;

}

