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
package de.egore911.versioning.util.listener;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.googlecode.flyway.core.Flyway;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class StartupListener implements ServletContextListener {

	private static final Logger log = LoggerFactory
			.getLogger(StartupListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();

		try {
			InitialContext initialContext = new InitialContext();
			DataSource dataSource = (DataSource) initialContext
					.lookup("java:comp/env/jdbc/versioningDS");
			Flyway flyway = new Flyway();
			flyway.setDataSource(dataSource);
			flyway.migrate();
		} catch (NamingException e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// Nothing
	}

}
