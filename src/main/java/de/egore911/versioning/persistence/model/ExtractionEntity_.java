package de.egore911.versioning.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ExtractionEntity.class)
public abstract class ExtractionEntity_ extends de.egore911.appframework.persistence.model.IntegerDbObject_ {

	public static volatile SingularAttribute<ExtractionEntity, ActionExtractionEntity> actionExtraction;
	public static volatile SingularAttribute<ExtractionEntity, String> destination;
	public static volatile SingularAttribute<ExtractionEntity, String> source;

}

