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
