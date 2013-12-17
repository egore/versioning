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
package de.egore911.versioning.persistence.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class VersionTest {

	@Test
	public void testIsNewerThan2Dots() {
		Project project = new Project();
		Version version = new Version();
		version.setProject(project);
		version.setVcsTag("1.0.0");

		Version other = new Version();
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
		Project project = new Project();
		Version version = new Version();
		version.setProject(project);
		version.setVcsTag("1.0");

		Version other = new Version();
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
