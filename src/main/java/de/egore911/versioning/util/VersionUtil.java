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
