package de.egore911.versioning.util.vcs;

import org.junit.Assert;
import org.junit.Test;

import de.egore911.versioning.persistence.model.Vcs;
import de.egore911.versioning.persistence.model.VcsHost;

public class GitProviderTest {

	@Test
	public void testTagExistsSsh() {
		VcsHost vcsHost = new VcsHost();
		vcsHost.setVcs(Vcs.git);
		vcsHost.setUri("git@github.com:egore/dri-log-client.git");
		Provider provider = vcsHost.getProvider();
		Assert.assertTrue(provider.tagExists("dri-log-0.5.0"));
		Assert.assertFalse(provider.tagExists("dri-log-1234"));
	}

	@Test
	public void testTagExistsHttps() {
		VcsHost vcsHost = new VcsHost();
		vcsHost.setVcs(Vcs.git);
		vcsHost.setUri("https://github.com/egore/dri-log-client.git");
		Provider provider = vcsHost.getProvider();
		Assert.assertTrue(provider.tagExists("dri-log-0.5.0"));
		Assert.assertFalse(provider.tagExists("dri-log-1234"));
	}

}
