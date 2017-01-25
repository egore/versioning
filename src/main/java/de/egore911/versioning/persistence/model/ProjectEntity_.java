package de.egore911.versioning.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ProjectEntity.class)
public abstract class ProjectEntity_ extends de.egore911.appframework.persistence.model.IntegerDbObject_ {

	public static volatile SingularAttribute<ProjectEntity, String> vcsPath;
	public static volatile SingularAttribute<ProjectEntity, Boolean> deleted;
	public static volatile ListAttribute<ProjectEntity, VersionEntity> versions;
	public static volatile SingularAttribute<ProjectEntity, TagTransformerEntity> tagTransformer;
	public static volatile ListAttribute<ProjectEntity, ActionCopyEntity> actionCopies;
	public static volatile SingularAttribute<ProjectEntity, String> name;
	public static volatile SingularAttribute<ProjectEntity, VcsHostEntity> vcsHost;
	public static volatile SingularAttribute<ProjectEntity, String> description;
	public static volatile ListAttribute<ProjectEntity, ServerEntity> configuredServers;
	public static volatile ListAttribute<ProjectEntity, ActionCheckoutEntity> actionCheckouts;
	public static volatile ListAttribute<ProjectEntity, ActionReplacementEntity> actionReplacements;
	public static volatile ListAttribute<ProjectEntity, ActionExtractionEntity> actionExtractions;

}

