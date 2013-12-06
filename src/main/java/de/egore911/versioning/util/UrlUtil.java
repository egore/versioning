package de.egore911.versioning.util;

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
