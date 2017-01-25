package de.egore911.versioning.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TagTransformerEntity.class)
public abstract class TagTransformerEntity_ extends de.egore911.appframework.persistence.model.IntegerDbObject_ {

	public static volatile ListAttribute<TagTransformerEntity, ProjectEntity> projects;
	public static volatile SingularAttribute<TagTransformerEntity, String> name;
	public static volatile SingularAttribute<TagTransformerEntity, String> searchPattern;
	public static volatile SingularAttribute<TagTransformerEntity, String> replacementPattern;

}

