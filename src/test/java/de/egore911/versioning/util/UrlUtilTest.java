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
package de.egore911.versioning.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class UrlUtilTest {

	@Test
	public void testConcatenateUrlWithSlashesOnlyNulls() {
		Assert.assertEquals("",
				UrlUtil.concatenateUrlWithSlashes(null, null, null));
	}

	@Test
	public void testConcatenateUrlWithSlashesOnlyEmpty() {
		Assert.assertEquals("", UrlUtil.concatenateUrlWithSlashes("", "", ""));
	}

	@Test
	public void testConcatenateUrlWithSlashesOnlyNullsAndEmpty() {
		Assert.assertEquals("",
				UrlUtil.concatenateUrlWithSlashes(null, null, ""));
		Assert.assertEquals("",
				UrlUtil.concatenateUrlWithSlashes(null, "", null));
		Assert.assertEquals("",
				UrlUtil.concatenateUrlWithSlashes("", null, null));
		Assert.assertEquals("", UrlUtil.concatenateUrlWithSlashes(null, "", ""));
		Assert.assertEquals("", UrlUtil.concatenateUrlWithSlashes("", null, ""));
		Assert.assertEquals("", UrlUtil.concatenateUrlWithSlashes("", "", null));
	}

	@Test
	public void testConcatenateUrlWithSlashesOnlyWithoutAnySlashes() {
		Assert.assertEquals("http://github.com/egore/versioning", UrlUtil
				.concatenateUrlWithSlashes("http://github.com", "egore",
						"versioning"));
		Assert.assertEquals("http://github.com/egore/versioning", UrlUtil
				.concatenateUrlWithSlashes("http://github.com",
						"egore/versioning"));
	}

	@Test
	public void testConcatenateUrlWithSlashesOnlyWithSlashesAtEnd() {
		Assert.assertEquals("http://github.com/egore/versioning/", UrlUtil
				.concatenateUrlWithSlashes("http://github.com/", "egore/",
						"versioning/"));
		Assert.assertEquals("http://github.com/egore/versioning/", UrlUtil
				.concatenateUrlWithSlashes("http://github.com/",
						"egore/versioning/"));
	}

	@Test
	public void testConcatenateUrlWithSlashesOnlyWithSlashesAtBeginning() {
		Assert.assertEquals("http://github.com/egore/versioning", UrlUtil
				.concatenateUrlWithSlashes("http://github.com", "/egore",
						"/versioning"));
		Assert.assertEquals("http://github.com/egore/versioning", UrlUtil
				.concatenateUrlWithSlashes("http://github.com",
						"/egore/versioning"));
	}

	@Test
	public void testConcatenateUrlWithSlashesOnlyWithSlashesAtBeginningAndEnd() {
		Assert.assertEquals("http://github.com/egore/versioning/", UrlUtil
				.concatenateUrlWithSlashes("http://github.com/", "/egore/",
						"/versioning/"));
		Assert.assertEquals("http://github.com/egore/versioning/", UrlUtil
				.concatenateUrlWithSlashes("http://github.com/",
						"/egore/versioning/"));
	}

	@Test
	public void testConcatenateUrlWithSlashesOnlyWithSlashOnlyArgument() {
		Assert.assertEquals("http://github.com/egore/versioning/", UrlUtil
				.concatenateUrlWithSlashes(
						"http://github.com/egore/versioning/", "/"));
		Assert.assertEquals("http://github.com/egore/versioning/", UrlUtil
				.concatenateUrlWithSlashes(
						"http://github.com/egore/versioning", "/"));
	}

}
