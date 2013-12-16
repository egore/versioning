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
package de.egore911.versioning.util.vcs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logger to pipe the jsch-logging into slf4j. Will be using the logging
 * category <code>com.jcraft.jsch</code>.
 * 
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
final class JschToSlf4j implements com.jcraft.jsch.Logger {

	private static final Logger log = LoggerFactory
			.getLogger("com.jcraft.jsch");

	@Override
	public void log(int level, String message) {
		switch (level) {
		case com.jcraft.jsch.Logger.DEBUG:
			log.debug(message);
			break;
		case com.jcraft.jsch.Logger.INFO:
			log.info(message);
			break;
		case com.jcraft.jsch.Logger.WARN:
			log.warn(message);
			break;
		case com.jcraft.jsch.Logger.ERROR:
		case com.jcraft.jsch.Logger.FATAL:
			log.error(message);
			break;
		default:
			log.info(message);
			break;
		}
	}

	@Override
	public boolean isEnabled(int level) {
		switch (level) {
		case com.jcraft.jsch.Logger.DEBUG:
			return log.isDebugEnabled();
		case com.jcraft.jsch.Logger.INFO:
			return log.isInfoEnabled();
		case com.jcraft.jsch.Logger.WARN:
			return log.isWarnEnabled();
		case com.jcraft.jsch.Logger.ERROR:
		case com.jcraft.jsch.Logger.FATAL:
			return log.isErrorEnabled();
		default:
			return log.isInfoEnabled();
		}
	}
}