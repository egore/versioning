package de.egore911.versioning.persistence.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BinaryData.class)
public abstract class BinaryData_ extends de.egore911.versioning.persistence.model.IntegerDbObject_ {

	public static volatile SingularAttribute<BinaryData, byte[]> data;
	public static volatile SingularAttribute<BinaryData, String> filename;
	public static volatile SingularAttribute<BinaryData, String> contentType;
	public static volatile SingularAttribute<BinaryData, Long> size;

}

