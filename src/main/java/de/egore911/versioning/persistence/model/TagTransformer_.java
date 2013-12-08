package de.egore911.versioning.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TagTransformer.class)
public abstract class TagTransformer_ extends de.egore911.versioning.persistence.model.IntegerDbObject_ {

	public static volatile ListAttribute<TagTransformer, Project> projects;
	public static volatile SingularAttribute<TagTransformer, String> name;
	public static volatile SingularAttribute<TagTransformer, String> searchPattern;
	public static volatile SingularAttribute<TagTransformer, String> replacementPattern;

}

