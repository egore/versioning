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
package de.egore911.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.tool.hbm2ddl.SimpleSchemaExport;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egore911.versioning.persistence.dao.ProjectDao;
import de.egore911.versioning.persistence.dao.ServerDao;
import de.egore911.versioning.persistence.dao.VcshostDao;
import de.egore911.versioning.persistence.dao.VersionDao;
import de.egore911.versioning.persistence.model.Project;
import de.egore911.versioning.persistence.model.Server;
import de.egore911.versioning.persistence.model.VcsHost;
import de.egore911.versioning.persistence.model.Version;
import de.egore911.versioning.ui.logic.DeploymentCalculator;
import de.egore911.versioning.util.listener.StartupListener;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class ImportSqlTest {

	private static final Logger log = LoggerFactory
			.getLogger(ImportSqlTest.class);

	@Inject
	private ProjectDao projectDao;
	@Inject
	private ServerDao serverDao;
	@Inject
	private VcshostDao vcshostDao;
	@Inject
	private VersionDao versionDao;

	@Before
	public void before() {
		System.setProperty("java.naming.factory.initial",
				"de.egore911.test.JndiFactory");

		new StartupListener().contextInitialized(null);
		try {
			InitialContext initialContext = new InitialContext();
			DataSource dataSource = (DataSource) initialContext
					.lookup("java:/comp/env/jdbc/versioningDS");
			try (Connection connection = dataSource.getConnection()) {
				new SimpleSchemaExport()
						.importScript(connection, "/import.sql");
			}
		} catch (NamingException | SQLException e) {
			log.error(e.getMessage(), e);
		}

		// TODO create entitymanagerfactory
	}

	@Test
	public void testImportSql() {

		// We have 2 VCS hosts in our import.sql
		List<VcsHost> vcshosts = vcshostDao.findAll();
		Assert.assertNotNull(vcshosts);
		Assert.assertEquals(2, vcshosts.size());

		// We have 3 projects in our import.sql
		List<Project> projects = projectDao.findAll();
		Assert.assertNotNull(projects);
		Assert.assertEquals(3, projects.size());

		// We have 5 versions in our import.sql
		List<Version> versions = versionDao.findAll();
		Assert.assertNotNull(versions);
		Assert.assertEquals(5, versions.size());

		// We have 5 versions in our import.sql
		List<Server> servers = serverDao.findAll();
		Assert.assertNotNull(servers);
		Assert.assertEquals(2, servers.size());

		// Load the first server
		Server server = serverDao.findById(1);
		Assert.assertEquals("production_server", server.getName());

		DeploymentCalculator deploymentCalculator = new DeploymentCalculator();
		List<Version> deployableVersions = deploymentCalculator
				.getDeployableVersions(server);
		Assert.assertEquals(1, deployableVersions.size());
		Assert.assertEquals("1.0.1", deployableVersions.iterator().next()
				.getVcsTag());
		Assert.assertEquals("some_svn_project", deployableVersions.iterator()
				.next().getProject().getName());

	}
}
