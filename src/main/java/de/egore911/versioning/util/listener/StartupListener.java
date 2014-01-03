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

import java.sql.Connection;
import java.sql.SQLException;

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
 * Listener executed during startup, responsible for setting up the
 * java.util.Logging->SLF4J bridge and updating the database.
 * 
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
					.lookup("java:/comp/env/jdbc/versioningDS");
			Flyway flyway = new Flyway();
			flyway.setDataSource(dataSource);

			switch (dataSource.getClass().getName()) {
			case "org.hsqldb.jdbc.JDBCDataSource":
				// Plain datasource for HSQLDB
				flyway.setLocations("db/migration/hsqldb");
				break;
			case "com.mysql.jdbc.jdbc2.optional.MysqlDataSource":
			case "com.mysql.jdbc.jdbc2.optional.MysqlXADataSource":
				// Plain datasource for MySQL/MariaDB
				flyway.setLocations("db/migration/mysql");
				break;
			case "org.apache.tomcat.dbcp.dbcp.BasicDataSource":

				// Wrapped by Tomcat
				try (Connection connection = dataSource.getConnection()) {
					// Deal with raw
					if (connection.toString().startsWith("jdbc:mysql://")) {
						flyway.setLocations("db/migration/mysql");
					} else if (connection.toString().startsWith("jdbc:hsqldb")) {
						flyway.setLocations("db/migration/hsqldb");
					} else {
						throw new RuntimeException(
								"Unsupported database detected, please report this: "
										+ connection.toString());
					}
				} catch (SQLException e) {
					log.error("Error opening connection :{}", e.getMessage(), e);
					return;
				}
				break;

			case "org.jboss.jca.adapters.jdbc.WrapperDataSource":
				try {
					if (dataSource
							.isWrapperFor(com.mysql.jdbc.jdbc2.optional.MysqlDataSource.class)
							|| dataSource
									.isWrapperFor(com.mysql.jdbc.jdbc2.optional.MysqlXADataSource.class)) {
						flyway.setLocations("db/migration/mysql");
					}
				} catch (Exception e) {
					// Do nothing
				}
				try {
					if (flyway.getLocations().length == 0
							&& dataSource
									.isWrapperFor(org.hsqldb.jdbc.JDBCDataSource.class)) {
						flyway.setLocations("db/migration/hsqldb");
					}
				} catch (Exception e) {
					// Do nothing
				}

				// FIXME: hack because the upper code refuses to work
				flyway.setLocations("db/migration/mysql");

				if (flyway.getLocations().length == 0) {
					throw new RuntimeException(
							"Unsupported database detected, please report this: "
									+ dataSource.getClass().getName());
				}

				break;
			default:
				throw new RuntimeException(
						"Unsupported database detected, please report this: "
								+ dataSource.getClass().getName());
			}

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
