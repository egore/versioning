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

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class ImportSqlTest {

	private EntityManagerFactory emf;

	@Before
	public void before() {
		System.setProperty("java.naming.factory.initial",
				"de.egore911.test.JndiFactory");

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
