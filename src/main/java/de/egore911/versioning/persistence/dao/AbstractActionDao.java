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

import de.egore911.persistence.dao.AbstractDao;
import de.egore911.versioning.persistence.model.AbstractActionEntity;
import de.egore911.versioning.persistence.model.ProjectEntity;
import de.egore911.versioning.persistence.selector.AbstractActionSelector;

import java.util.List;

public abstract class AbstractActionDao<T extends AbstractActionEntity>
		extends AbstractDao<T> {

	@Override
	protected abstract AbstractActionSelector<T> createSelector();

	public List<T> findByIds(List<Integer> ids) {
		return createSelector()
				.withIds(ids)
				.findAll();
	}

	public List<T> findByProject(ProjectEntity project) {
		return createSelector()
				.withProject(project)
				.findAll();
	}
}
