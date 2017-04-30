package de.egore911.versioning.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import de.egore911.appframework.persistence.model.BinaryDataEntity;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ServerEntity.class)
public abstract class ServerEntity_ extends de.egore911.appframework.persistence.model.IntegerDbObject_ {

	public static volatile SingularAttribute<ServerEntity, Integer> iconId;
	public static volatile SingularAttribute<ServerEntity, String> vcsPath;
	public static volatile ListAttribute<ServerEntity, VariableEntity> variables;
	public static volatile ListAttribute<ServerEntity, ProjectEntity> configuredProjects;
	public static volatile SingularAttribute<ServerEntity, String> name;
	public static volatile SingularAttribute<ServerEntity, VcsHostEntity> vcsHost;
	public static volatile SingularAttribute<ServerEntity, BinaryDataEntity> icon;
	public static volatile SingularAttribute<ServerEntity, String> description;
	public static volatile SingularAttribute<ServerEntity, String> targetPath;
	public static volatile ListAttribute<ServerEntity, ActionReplacementEntity> actionReplacements;
	public static volatile SingularAttribute<ServerEntity, String> targetdir;

}

