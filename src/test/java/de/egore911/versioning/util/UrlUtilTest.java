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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEmptyString.isEmptyString;
import org.junit.Test;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class UrlUtilTest {

	@Test
	public void testConcatenateUrlWithSlashesOnlyNulls() {
		assertThat(UrlUtil.concatenateUrlWithSlashes(null, null, null),
				isEmptyString());
	}

	@Test
	public void testConcatenateUrlWithSlashesOnlyEmpty() {
		assertThat(UrlUtil.concatenateUrlWithSlashes("", "", ""),
				isEmptyString());
	}

	@Test
	public void testConcatenateUrlWithSlashesOnlyNullsAndEmpty() {
		assertThat(UrlUtil.concatenateUrlWithSlashes(null, null, ""),
				isEmptyString());
		assertThat(UrlUtil.concatenateUrlWithSlashes(null, "", null),
				isEmptyString());
		assertThat(UrlUtil.concatenateUrlWithSlashes("", null, null),
				isEmptyString());
		assertThat(UrlUtil.concatenateUrlWithSlashes(null, "", ""),
				isEmptyString());
		assertThat(UrlUtil.concatenateUrlWithSlashes("", null, ""),
				isEmptyString());
		assertThat(UrlUtil.concatenateUrlWithSlashes("", "", null),
				isEmptyString());
	}

	@Test
	public void testConcatenateUrlWithSlashesOnlyWithoutAnySlashes() {
		assertThat(
				UrlUtil.concatenateUrlWithSlashes("http://github.com", "egore",
						"versioning"),
				equalTo("http://github.com/egore/versioning"));
		assertThat(
				UrlUtil.concatenateUrlWithSlashes("http://github.com",
						"egore/versioning"),
				equalTo("http://github.com/egore/versioning"));
	}

	@Test
	public void testConcatenateUrlWithSlashesOnlyWithSlashesAtEnd() {
		assertThat(
				UrlUtil.concatenateUrlWithSlashes("http://github.com/",
						"egore/", "versioning/"),
				equalTo("http://github.com/egore/versioning/"));
		assertThat(
				UrlUtil.concatenateUrlWithSlashes("http://github.com/",
						"egore/versioning/"),
				equalTo("http://github.com/egore/versioning/"));
	}

	@Test
	public void testConcatenateUrlWithSlashesOnlyWithSlashesAtBeginning() {
		assertThat(
				UrlUtil.concatenateUrlWithSlashes("http://github.com", "/egore",
						"/versioning"),
				equalTo("http://github.com/egore/versioning"));
		assertThat(
				UrlUtil.concatenateUrlWithSlashes("http://github.com",
						"/egore/versioning"),
				equalTo("http://github.com/egore/versioning"));
	}

	@Test
	public void testConcatenateUrlWithSlashesOnlyWithSlashesAtBeginningAndEnd() {
		assertThat(
				UrlUtil.concatenateUrlWithSlashes("http://github.com/",
						"/egore/", "/versioning/"),
				equalTo("http://github.com/egore/versioning/"));
		assertThat(
				UrlUtil.concatenateUrlWithSlashes("http://github.com/",
						"/egore/versioning/"),
				equalTo("http://github.com/egore/versioning/"));
	}

	@Test
	public void testConcatenateUrlWithSlashesOnlyWithSlashOnlyArgument() {
		assertThat(
				UrlUtil.concatenateUrlWithSlashes(
						"http://github.com/egore/versioning/", "/"),
				equalTo("http://github.com/egore/versioning/"));
		assertThat(
				UrlUtil.concatenateUrlWithSlashes(
						"http://github.com/egore/versioning", "/"),
				equalTo("http://github.com/egore/versioning/"));
	}

}
