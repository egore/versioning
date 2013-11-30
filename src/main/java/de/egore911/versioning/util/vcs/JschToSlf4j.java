package de.egore911.versioning.util.vcs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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