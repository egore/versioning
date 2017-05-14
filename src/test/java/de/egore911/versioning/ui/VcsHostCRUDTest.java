package de.egore911.versioning.ui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.core.GenericType;

import de.egore911.appframework.testing.ui.AbstractCRUDTest;
import de.egore911.versioning.persistence.model.Vcs;
import de.egore911.versioning.ui.dto.VcsHost;

public class VcsHostCRUDTest extends AbstractCRUDTest<VcsHost> {

	@Override
	protected VcsHost createFixture() {
		VcsHost vcsHost = new VcsHost();
		vcsHost.setName(UUID.randomUUID().toString());
		vcsHost.setUri(UUID.randomUUID().toString());
		vcsHost.setVcs(Vcs.git);
		return vcsHost;
	}

	@Override
	protected String getPath() {
		return "vcs_host";
	}

	@Override
	protected Class<VcsHost> getFixtureClass() {
		return VcsHost.class;
	}

	@Override
	protected void compareDtos(VcsHost fixture, VcsHost created) {
		assertThat(created.getName(), equalTo(fixture.getName()));
		assertThat(created.getUri(), equalTo(fixture.getUri()));
		assertThat(created.getVcs(), equalTo(fixture.getVcs()));
	}

	@Override
	protected void modifyFixture(VcsHost fixture) {
		fixture.setUri(UUID.randomUUID().toString());
	}

	@Override
	protected GenericType<List<VcsHost>> getGenericType() {
		return new GenericType<List<VcsHost>>() {
		};
	}

}
