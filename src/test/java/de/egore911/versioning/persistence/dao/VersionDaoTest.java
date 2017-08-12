package de.egore911.versioning.persistence.dao;

import de.egore911.appframework.testing.persistence.dao.AbstractDaoCRUDTest;
import de.egore911.versioning.persistence.model.ProjectEntity;
import de.egore911.versioning.persistence.model.VersionEntity;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

public class VersionDaoTest extends AbstractDaoCRUDTest<VersionEntity> {

    @Override
    protected VersionEntity createFixture() {
        ProjectEntity project = new ProjectDaoTest().createFixture();
        project = new ProjectDao().save(project);

        VersionEntity mavenRepository = new VersionEntity();
        mavenRepository.setProject(project);
        mavenRepository.setVcsTag(UUID.randomUUID().toString());
        return mavenRepository;
    }

    @Override
    protected void modifyFixture(VersionEntity version) {
        version.setVcsTag(UUID.randomUUID().toString());
    }

    @Override
    protected VersionDao getDao() {
        return new VersionDao();
    }

    @Test
    public void testTagExists() {

        ProjectEntity someSvnProject = new ProjectDao().findById(1);
        assertThat(someSvnProject, notNullValue());

        assertThat(getDao().tagExists(someSvnProject, "1.0.0"), equalTo(true));
        assertThat(getDao().tagExists(someSvnProject, "1.0.1"), equalTo(true));

        assertThat(getDao().tagExists(someSvnProject, "1.0.0."), equalTo(false));
        assertThat(getDao().tagExists(someSvnProject, "1.0.1.0"), equalTo(false));
        assertThat(getDao().tagExists(someSvnProject, "3.0.0"), equalTo(false));
    }
}
