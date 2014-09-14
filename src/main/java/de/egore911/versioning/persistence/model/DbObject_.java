package de.egore911.versioning.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import org.joda.time.LocalDateTime;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DbObject.class)
public abstract class DbObject_ {

	public static volatile SingularAttribute<DbObject<?>, User> createdBy;
	public static volatile SingularAttribute<DbObject<?>, LocalDateTime> created;
	public static volatile SingularAttribute<DbObject<?>, User> modifiedBy;
	public static volatile SingularAttribute<DbObject<?>, LocalDateTime> modified;

}

