/*
 * Copyright 2013  Christoph Brill <egore911@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package de.egore911.versioning.util.vcs;

import org.junit.Assert;
import org.junit.Test;

import de.egore911.versioning.persistence.model.Project;
import de.egore911.versioning.persistence.model.Vcs;
import de.egore911.versioning.persistence.model.VcsHost;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class SvnProviderTest {

	@Test
	public void testTagExistsHttps() {
		VcsHost vcsHost = new VcsHost();
		vcsHost.setVcs(Vcs.svn);
		vcsHost.setUri("https://svn.apache.org/repos/asf/");
		Project project = new Project();
		project.setVcsHost(vcsHost);
		project.setVcsPath("maven/maven-3/");
		Provider provider = project.getProvider();
		Assert.assertTrue(provider.tagExists(project, "maven-3.0.4"));
		Assert.assertFalse(provider.tagExists(project, "maven-1234"));
	}

	@Test
	public void testTagExistsHttpsWithTrunk() {
		VcsHost vcsHost = new VcsHost();
		vcsHost.setVcs(Vcs.svn);
		vcsHost.setUri("https://svn.apache.org/repos/asf/");
		Project project = new Project();
		project.setVcsHost(vcsHost);
		project.setVcsPath("maven/maven-3/trunk");
		Provider provider = project.getProvider();
		Assert.assertTrue(provider.tagExists(project, "maven-3.0.4"));
		Assert.assertFalse(provider.tagExists(project, "maven-1234"));
	}

	@Test
	public void testTagExistsHttpsWithTrunkSlash() {
		VcsHost vcsHost = new VcsHost();
		vcsHost.setVcs(Vcs.svn);
		vcsHost.setUri("https://svn.apache.org/repos/asf/");
		Project project = new Project();
		project.setVcsHost(vcsHost);
		project.setVcsPath("maven/maven-3/trunk/");
		Provider provider = project.getProvider();
		Assert.assertTrue(provider.tagExists(project, "maven-3.0.4"));
		Assert.assertFalse(provider.tagExists(project, "maven-1234"));
	}

}
