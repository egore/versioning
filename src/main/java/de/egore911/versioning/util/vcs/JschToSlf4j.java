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