package de.egore911.versioning.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(VcsHostEntity.class)
public abstract class VcsHostEntity_ extends de.egore911.appframework.persistence.model.IntegerDbObject_ {

	public static volatile SingularAttribute<VcsHostEntity, String> password;
	public static volatile ListAttribute<VcsHostEntity, ProjectEntity> projects;
	public static volatile SingularAttribute<VcsHostEntity, Vcs> vcs;
	public static volatile SingularAttribute<VcsHostEntity, String> name;
	public static volatile SingularAttribute<VcsHostEntity, String> uri;
	public static volatile SingularAttribute<VcsHostEntity, String> username;

}

