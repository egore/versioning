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

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class UrlUtil {

	public String concatenateUrlWithSlashes(String... urlParts) {
		StringBuilder builder = new StringBuilder();
		for (String urlPart : urlParts) {
			if (urlPart == null) {
				// called with null argument, skipping
				continue;
			}
			if (urlPart.isEmpty()) {
				// called with empty argument, skipping
				continue;
			}
			if (builder.length() == 0) {
				// First part, simply append
				builder.append(urlPart);
			} else {
				if (builder.charAt(builder.length() - 1) == '/') {
					// last part ends with '/'
					if (urlPart.charAt(0) == '/') {
						// Current part starts with '/', skip double slash
						builder.append(urlPart.substring(1));
					} else {
						// Current part does not start with '/', simply append
						builder.append(urlPart);
					}
				} else {
					// last part does not end with '/'
					if (urlPart.charAt(0) != '/') {
						// Current part does not start with '/', append it
						builder.append('/');
					}
					builder.append(urlPart);
				}
			}
		}
		return builder.toString();
	}
}
