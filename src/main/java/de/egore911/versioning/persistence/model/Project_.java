package de.egore911.versioning.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Project.class)
public abstract class Project_ extends de.egore911.versioning.persistence.model.IntegerDbObject_ {

	public static volatile SingularAttribute<Project, String> vcsPath;
	public static volatile ListAttribute<Project, Version> versions;
	public static volatile SingularAttribute<Project, TagTransformer> tagTransformer;
	public static volatile ListAttribute<Project, ActionCopy> actionCopies;
	public static volatile SingularAttribute<Project, String> name;
	public static volatile SingularAttribute<Project, VcsHost> vcsHost;
	public static volatile SingularAttribute<Project, String> description;
	public static volatile ListAttribute<Project, Server> configuredServers;
	public static volatile ListAttribute<Project, ActionCheckout> actionCheckouts;
	public static volatile ListAttribute<Project, ActionReplacement> actionReplacements;
	public static volatile ListAttribute<Project, ActionExtraction> actionExtractions;

}

