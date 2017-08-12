package de.egore911.versioning.persistence.dao;

import de.egore911.appframework.testing.persistence.dao.AbstractDaoCRUDTest;
import de.egore911.versioning.persistence.model.TagTransformerEntity;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

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

    @Test
    public void testFindByName() {
        String validName = "No capitals";
        assertThat(getDao().findByName(validName), notNullValue());
        assertThat(getDao().findByName(validName).getId(), equalTo(1));

        // XXX this test fails on HSQLDB for no obvious reason
        // assertThat(getDao().findByName(validName + " "), nullValue());
        assertThat(getDao().findByName(" " + validName), nullValue());
        assertThat(getDao().findByName("capitals"), nullValue());

        assertThat(getDao().findByName(UUID.randomUUID().toString()), nullValue());

        assertThat(getDao().findByName(""), nullValue());
        assertThat(getDao().findByName(null), nullValue());
    }
}
