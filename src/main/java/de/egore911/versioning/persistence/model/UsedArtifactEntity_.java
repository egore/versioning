package de.egore911.versioning.persistence.model;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UsedArtifactEntity.class)
public abstract class UsedArtifactEntity_ extends de.egore911.versioning.persistence.model.AbstractArtifactEntity_ {

	public static volatile SingularAttribute<UsedArtifactEntity, LocalDateTime> lastSeen;
	public static volatile SingularAttribute<UsedArtifactEntity, VerificationEntity> verification;

}

