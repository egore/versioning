/*
 * Copyright 2013-2015  Christoph Brill <egore911@gmail.com>
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

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.egore911.appframework.testing.AbstractDatabaseTest;
import de.egore911.versioning.persistence.dao.ProjectDao;
import de.egore911.versioning.persistence.dao.ServerDao;
import de.egore911.versioning.persistence.dao.VcsHostDao;
import de.egore911.versioning.persistence.dao.VersionDao;
import de.egore911.versioning.persistence.model.ProjectEntity;
import de.egore911.versioning.persistence.model.ServerEntity;
import de.egore911.versioning.persistence.model.VcsHostEntity;
import de.egore911.versioning.persistence.model.VersionEntity;
import de.egore911.versioning.ui.logic.DeploymentCalculator;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class ImportSqlTest extends AbstractDatabaseTest {

	@Test
	public void testImportSql() {

		// We have 2 VCS hosts in our import.sql
		VcsHostDao vcshostDao = new VcsHostDao();
		List<VcsHostEntity> vcshosts = vcshostDao.findAll();
		Assert.assertNotNull(vcshosts);
		Assert.assertEquals(2, vcshosts.size());

		// We have 3 projects in our import.sql
		ProjectDao projectDao = new ProjectDao();
		List<ProjectEntity> projects = projectDao.findAll();
		Assert.assertNotNull(projects);
		Assert.assertEquals(3, projects.size());

		// We have 5 versions in our import.sql
		VersionDao versionDao = new VersionDao();
		List<VersionEntity> versions = versionDao.findAll();
		Assert.assertNotNull(versions);
		Assert.assertEquals(5, versions.size());

		// We have 5 versions in our import.sql
		ServerDao serverDao = new ServerDao();
		List<ServerEntity> servers = serverDao.findAll();
		Assert.assertNotNull(servers);
		Assert.assertEquals(2, servers.size());

		// Load the first server
		ServerEntity server = serverDao.findById(1);
		Assert.assertEquals("production_server", server.getName());

		DeploymentCalculator deploymentCalculator = new DeploymentCalculator();
		List<VersionEntity> deployableVersions = deploymentCalculator
				.getDeployableVersions(server);
		Assert.assertEquals(1, deployableVersions.size());
		Assert.assertEquals("1.0.1",
				deployableVersions.iterator().next().getVcsTag());
		Assert.assertEquals("some_svn_project",
				deployableVersions.iterator().next().getProject().getName());

	}
}
