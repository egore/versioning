/*
 * Copyright 2013  Christoph Brill <egore911@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.egore911.versioning.util.vcs;

import org.junit.Assert;
import org.junit.Test;

import de.egore911.versioning.persistence.model.Vcs;
import de.egore911.versioning.persistence.model.VcsHost;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
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
