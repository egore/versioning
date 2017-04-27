package de.egore911.versioning.persistence.selector;

import de.egore911.appframework.persistence.selector.AbstractResourceSelector;
import de.egore911.versioning.persistence.model.AbstractArtifactEntity;
import de.egore911.versioning.persistence.model.AbstractArtifactEntity_;
import de.egore911.versioning.persistence.model.VerificationEntity;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author Christoph Brill &lt;christoph.brill@cgm.com&gt;
 * @since 27.04.2017 12:36
 */
public abstract class AbstractArtifactSelector<T extends AbstractArtifactEntity> extends AbstractResourceSelector<T> {

    private static final long serialVersionUID = -4687491428631207216L;

    private String search;
    private String groupId;
    private String artifactId;
    private String version;

    @Nonnull
    @Override
    protected List<Predicate> generatePredicateList(@Nonnull CriteriaBuilder builder,
                                                    @Nonnull Root<T> from,
                                                    @Nonnull CriteriaQuery<?> criteriaQuery) {
        List<Predicate> predicates = super.generatePredicateList(builder, from,
                criteriaQuery);

        if (StringUtils.isNotEmpty(search)) {
            String likePattern = '%' + search + '%';
            predicates.add(
                    builder.or(
                            builder.like(from.get(AbstractArtifactEntity_.groupId), likePattern),
                            builder.like(from.get(AbstractArtifactEntity_.artifactId), likePattern),
                            builder.like(from.get(AbstractArtifactEntity_.version), likePattern)
                    )
            );
        }

        if (StringUtils.isNotEmpty(groupId)) {
            predicates.add(builder.or(
                    builder.equal(from.get(AbstractArtifactEntity_.groupId), groupId),
                    builder.equal(from.get(AbstractArtifactEntity_.groupId), "*")
            ));
        }

        if (StringUtils.isNotEmpty(artifactId)) {
            predicates.add(builder.or(
                    builder.equal(from.get(AbstractArtifactEntity_.artifactId), artifactId),
                    builder.equal(from.get(AbstractArtifactEntity_.artifactId), "*")
            ));
        }

        if (StringUtils.isNotEmpty(version)) {
            predicates.add(builder.or(
                    builder.equal(from.get(AbstractArtifactEntity_.version), version),
                    builder.equal(from.get(AbstractArtifactEntity_.version), "*")
            ));
        }

        return predicates;
    }

    @Override
    public AbstractArtifactSelector<T> withSearch(
            String search) {
        this.search = search;
        return this;
    }

    public AbstractArtifactSelector<T> withGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public AbstractArtifactSelector<T> withArtifactId(String artifactId) {
        this.artifactId = artifactId;
        return this;
    }

    public AbstractArtifactSelector<T> withVersion(String version) {
        this.version = version;
        return this;
    }

}
