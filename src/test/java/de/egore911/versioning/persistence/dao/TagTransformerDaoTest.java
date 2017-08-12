package de.egore911.versioning.persistence.dao;

import de.egore911.appframework.testing.persistence.dao.AbstractDaoCRUDTest;
import de.egore911.versioning.persistence.model.TagTransformerEntity;

import java.util.UUID;

public class TagTransformerDaoTest extends AbstractDaoCRUDTest<TagTransformerEntity> {

    @Override
    protected TagTransformerEntity createFixture() {
        TagTransformerEntity tagTransformer = new TagTransformerEntity();
        tagTransformer.setName(UUID.randomUUID().toString());
        tagTransformer.setReplacementPattern(UUID.randomUUID().toString());
        tagTransformer.setSearchPattern(UUID.randomUUID().toString());
        return tagTransformer;
    }

    @Override
    protected void modifyFixture(TagTransformerEntity tagTransformer) {
        tagTransformer.setReplacementPattern(UUID.randomUUID().toString());
    }

    @Override
    protected TagTransformerDao getDao() {
        return new TagTransformerDao();
    }

}
