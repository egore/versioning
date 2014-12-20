package de.egore911.versioning.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(VcsHost.class)
public abstract class VcsHost_ extends de.egore911.versioning.persistence.model.IntegerDbObject_ {

	public static volatile SingularAttribute<VcsHost, String> password;
	public static volatile ListAttribute<VcsHost, Project> projects;
	public static volatile SingularAttribute<VcsHost, Vcs> vcs;
	public static volatile SingularAttribute<VcsHost, String> name;
	public static volatile SingularAttribute<VcsHost, String> uri;
	public static volatile SingularAttribute<VcsHost, String> username;

}

