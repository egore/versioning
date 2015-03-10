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
package de.egore911.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.hibernate.tool.hbm2ddl.SimpleSchemaExport;
import org.junit.After;
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
import de.egore911.versioning.util.EntityManagerUtil;
import de.egore911.versioning.util.listener.StartupListener;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class ImportSqlTest {

	private static final Logger log = LoggerFactory
			.getLogger(ImportSqlTest.class);

	private EntityManagerFactory emf;

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

		emf = Persistence.createEntityManagerFactory("versioning");
		EntityManager em = emf.createEntityManager();
		EntityManagerUtil.setEntityManager(em);
	}

	@After
	public void after() {
		EntityManager em = EntityManagerUtil.getEntityManager();
		EntityManagerUtil.clearEntityManager();
		em.close();
		emf.close();
	}

	@Test
	public void testImportSql() {

		// We have 2 VCS hosts in our import.sql
		VcshostDao vcshostDao = new VcshostDao();
		List<VcsHost> vcshosts = vcshostDao.findAll();
		Assert.assertNotNull(vcshosts);
		Assert.assertEquals(2, vcshosts.size());

		// We have 3 projects in our import.sql
		ProjectDao projectDao = new ProjectDao();
		List<Project> projects = projectDao.findAll();
		Assert.assertNotNull(projects);
		Assert.assertEquals(3, projects.size());

		// We have 5 versions in our import.sql
		VersionDao versionDao = new VersionDao();
		List<Version> versions = versionDao.findAll();
		Assert.assertNotNull(versions);
		Assert.assertEquals(5, versions.size());

		// We have 5 versions in our import.sql
		ServerDao serverDao = new ServerDao();
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
