package de.egore911.versioning.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Server.class)
public abstract class Server_ extends de.egore911.versioning.persistence.model.IntegerDbObject_ {

	public static volatile SingularAttribute<Server, Integer> iconId;
	public static volatile SingularAttribute<Server, String> vcsPath;
	public static volatile ListAttribute<Server, Variable> variables;
	public static volatile ListAttribute<Server, Project> configuredProjects;
	public static volatile SingularAttribute<Server, String> name;
	public static volatile SingularAttribute<Server, VcsHost> vcsHost;
	public static volatile SingularAttribute<Server, BinaryData> icon;
	public static volatile SingularAttribute<Server, String> description;
	public static volatile SingularAttribute<Server, String> targetPath;
	public static volatile ListAttribute<Server, ActionReplacement> actionReplacements;
	public static volatile SingularAttribute<Server, String> targetdir;

}

