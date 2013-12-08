package de.egore911.versioning.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Extraction.class)
public abstract class Extraction_ extends de.egore911.versioning.persistence.model.IntegerDbObject_ {

	public static volatile SingularAttribute<Extraction, String> source;
	public static volatile SingularAttribute<Extraction, ActionExtraction> actionExtraction;
	public static volatile SingularAttribute<Extraction, String> destination;

}

