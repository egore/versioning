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
package de.egore911.versioning.persistence.dao;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import org.apache.commons.collections4.CollectionUtils;

import de.egore911.persistence.dao.AbstractDao;
import de.egore911.versioning.persistence.model.ProjectEntity;
import de.egore911.versioning.persistence.model.ServerEntity;
import de.egore911.versioning.persistence.selector.ProjectSelector;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class ProjectDao extends AbstractDao<ProjectEntity> {

	@Override
	protected Class<ProjectEntity> getEntityClass() {
		return ProjectEntity.class;
	}

	@Override
	protected ProjectSelector createSelector() {
		return new ProjectSelector();
	}

	public List<ProjectEntity> getConfiguredProjects(ServerEntity server) {
		return createSelector()
				.withConfiguredForServer(server)
				.findAll();
	}

	public void delete(@Nonnull ProjectEntity project) {
		project.setDeleted(true);
		save(project);
	}

	public List<ProjectEntity> findAllNonDeleted() {
		return createSelector()
				.withExcludeDeleted(Boolean.TRUE)
				.findAll();
	}

	public List<ProjectEntity> findByIds(Collection<Integer> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.emptyList();
		}
		return createSelector()
				.withIds(ids)
				.findAll();
	}

}
