package de.egore911.versioning.util.vcs;

import org.junit.Assert;
import org.junit.Test;

import de.egore911.versioning.persistence.model.Vcs;
import de.egore911.versioning.persistence.model.VcsHost;

public class SvnProviderTest {

	@Test
	public void testTagExistsHttps() {
		VcsHost vcsHost = new VcsHost();
		vcsHost.setVcs(Vcs.svn);
		vcsHost.setUri("https://svn.apache.org/repos/asf/maven/maven-3/");
		Provider provider = vcsHost.getProvider();
		Assert.assertTrue(provider.tagExists("maven-3.0.4"));
		Assert.assertFalse(provider.tagExists("maven-1234"));
	}

}
