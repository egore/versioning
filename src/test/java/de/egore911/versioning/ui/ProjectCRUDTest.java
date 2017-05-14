package de.egore911.versioning.ui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.junit.Before;

import de.egore911.appframework.testing.ui.AbstractCRUDTest;
import de.egore911.versioning.ui.dto.Project;
import de.egore911.versioning.ui.dto.VcsHost;

public class ProjectCRUDTest extends AbstractCRUDTest<Project> {

	private VcsHost vcsHost;

	@Before
	public void before() {
		VcsHostCRUDTest vcsHostCRUDTest = new VcsHostCRUDTest();
		VcsHost fixture = vcsHostCRUDTest.createFixture();
		Entity<VcsHost> entity = Entity.entity(fixture,
				MediaType.APPLICATION_JSON);
		vcsHost = target(vcsHostCRUDTest.getPath()).request().post(entity,
				VcsHost.class);
	}

	@Override
	protected Project createFixture() {
		Project project = new Project();
		project.setName(UUID.randomUUID().toString());
		project.setDescription(UUID.randomUUID().toString());
		project.setVcsPath(UUID.randomUUID().toString());
		project.setVcsHostId(vcsHost.getId());
		return project;
	}

	@Override
	protected String getPath() {
		return "project";
	}

	@Override
	protected Class<Project> getFixtureClass() {
		return Project.class;
	}

	@Override
	protected void compareDtos(Project fixture, Project created) {
		assertThat(created.getName(), equalTo(fixture.getName()));
		assertThat(created.getDescription(), equalTo(fixture.getDescription()));
		assertThat(created.getVcsPath(), equalTo(fixture.getVcsPath()));
		assertThat(created.getVcsHostId(), equalTo(fixture.getVcsHostId()));
	}

	@Override
	protected void modifyFixture(Project fixture) {
		fixture.setDescription(UUID.randomUUID().toString());
	}

	@Override
	protected GenericType<List<Project>> getGenericType() {
		return new GenericType<List<Project>>() {
		};
	}

}
