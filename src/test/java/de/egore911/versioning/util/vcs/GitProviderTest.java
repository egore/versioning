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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import org.junit.Ignore;
import org.junit.Test;

import de.egore911.versioning.persistence.model.ProjectEntity;
import de.egore911.versioning.persistence.model.Vcs;
import de.egore911.versioning.persistence.model.VcsHostEntity;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class GitProviderTest {

	@Test
	@Ignore
	public void testTagExistsSsh() {
		VcsHostEntity vcsHost = new VcsHostEntity();
		vcsHost.setVcs(Vcs.git);
		vcsHost.setUri("git@github.com:");
		ProjectEntity project = new ProjectEntity();
		project.setVcsHost(vcsHost);
		project.setVcsPath("egore/dri-log-client.git");
		Provider provider = project.getProvider();
		assertThat(provider.tagExists("dri-log-0.5.0"), equalTo(true));
		assertThat(provider.tagExists("dri-log-1234"), equalTo(false));
	}

	@Test
	public void testTagExistsHttps() {
		VcsHostEntity vcsHost = new VcsHostEntity();
		vcsHost.setVcs(Vcs.git);
		vcsHost.setUri("https://github.com/");
		ProjectEntity project = new ProjectEntity();
		project.setVcsHost(vcsHost);
		project.setVcsPath("egore/dri-log-client.git");
		Provider provider = project.getProvider();
		assertThat(provider.tagExists("dri-log-0.5.0"), equalTo(true));
		assertThat(provider.tagExists("dri-log-1234"), equalTo(false));
	}

}
