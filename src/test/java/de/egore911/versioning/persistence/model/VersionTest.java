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
package de.egore911.versioning.persistence.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class VersionTest {

	@Test
	public void testIsNewerThan2Dots() {
		ProjectEntity project = new ProjectEntity();
		VersionEntity version = new VersionEntity();
		version.setProject(project);
		version.setVcsTag("1.0.0");

		VersionEntity other = new VersionEntity();
		other.setProject(project);

		// 1.0.0 is newer than 0.9.9
		other.setVcsTag("0.9.9");
		Assert.assertTrue(version.isNewerThan(other));

		// 1.0.0 is older than 1.0.1
		other.setVcsTag("1.0.1");
		Assert.assertFalse(version.isNewerThan(other));

		// 1.0.0 is the same as 1.0.0
		other.setVcsTag("1.0.0");
		Assert.assertFalse(version.isNewerThan(other));
		Assert.assertFalse(other.isNewerThan(version));

		// 1.0.0 is older than 1.0.10
		other.setVcsTag("1.0.10");
		Assert.assertFalse(version.isNewerThan(other));

		// 1.0.9 is older than 1.0.10
		version.setVcsTag("1.0.9");
		Assert.assertFalse(version.isNewerThan(other));
	}

	@Test
	public void testIsNewerThan1Dot() {
		ProjectEntity project = new ProjectEntity();
		VersionEntity version = new VersionEntity();
		version.setProject(project);
		version.setVcsTag("1.0");

		VersionEntity other = new VersionEntity();
		other.setProject(project);

		// 1.0 is newer than 0.9.9
		other.setVcsTag("0.9.9");
		Assert.assertTrue(version.isNewerThan(other));

		// 1.0 is older than 1.0.1
		other.setVcsTag("1.0.1");
		Assert.assertFalse(version.isNewerThan(other));

		// 1.0 is the same as 1.0.0
		other.setVcsTag("1.0.0");
		Assert.assertFalse(version.isNewerThan(other));
		Assert.assertFalse(other.isNewerThan(version));
	}
}
