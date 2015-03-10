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

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import de.egore911.versioning.persistence.model.Version;

/**
 * Utility containing helper methods regarding versions.
 * 
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class VersionUtil {

	private VersionUtil() {
	}

	/**
	 * Return the latest version from the given list of versions.
	 * 
	 * @param versions
	 *            the list of versions
	 * @return the latest version in the given list
	 */
	public static Version getLatestVersion(List<Version> versions) {
		if (CollectionUtils.isEmpty(versions)) {
			return null;
		}
		Version latest = versions.get(0);
		for (Version v : versions) {
			if (v.isNewerThan(latest)) {
				latest = v;
			}
		}
		return latest;
	}

	public static boolean isNewerThan(String tagA, String tagB) {
		int myDots = StringUtils.countMatches(tagA, ".");
		int otherDots = StringUtils.countMatches(tagB, ".");
		String[] mySplit = tagA.split("\\.");
		String[] otherSplit = tagB.split("\\.");
		int length = Math.min(mySplit.length, otherSplit.length);
		for (int i = 0; i < length; i++) {
			int compare = Integer.valueOf(mySplit[i]).compareTo(
					Integer.valueOf(otherSplit[i]));
			if (compare > 0) {
				return true;
			} else if (compare < 0) {
				return false;
			}
		}

		// Same version
		if (myDots == otherDots) {
			return false;
		} else if (myDots > otherDots) {
			// Controversial: Right now "1.0" is the same as "1.0.0.0.0"
			for (int i = otherDots; i < myDots; i++) {
				if (mySplit[i].equals("0")) {
					continue;
				}
				return true;
			}
			return false;
		} else {
			return false;
		}
	}
}
