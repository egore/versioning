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
package de.egore911.versioning.ui.beans.list;

import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;

import de.egore911.versioning.persistence.model.IntegerDbObject;
import de.egore911.versioning.persistence.selector.AbstractSelector;
import de.egore911.versioning.ui.model.PagingDataModel;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public abstract class AbstractList<T extends IntegerDbObject> {

	protected static final int DEFAULT_LIMIT = 20;

	@Inject
	protected EntityManager entityManager;

	public abstract AbstractSelector<T> getSelector();
	public abstract void setSelector(AbstractSelector<T> selector);

	public long count() {
		return getSelector().count();
	}

	public Integer getPage() {
		return (getOffset() / getLimit()) + 1;
	}

	private Integer getOffset() {
		Integer offset = getSelector().getOffset();
		if (offset == null) {
			offset = 0;
		}
		return offset;
	}

	private Integer getLimit() {
		Integer limit = getSelector().getLimit();
		if (limit == null || limit == 0) {
			limit = DEFAULT_LIMIT;
		}
		return limit;
	}

	public void setPage(Integer page) {
		if (page == null) {
			return;
		}
		getSelector().setOffset((page - 1) * getLimit());
	}

	public Integer getMaxPages() {
		return (int) Math.ceil((double) count() / getLimit());
	}

	private DataModel<T> dataModel;

	public DataModel<T> getDataModel() {
		if (dataModel == null) {
			AbstractSelector<T> selector = getSelector();
			dataModel = new PagingDataModel<>(selector.count(), selector);
		}
		return dataModel;
	}

	public String orderBy(String sortColumn) {
		if (StringUtils.equals(getSelector().getSortColumn(), sortColumn)) {
			getSelector()
					.setAscending(
							getSelector().getAscending() == Boolean.TRUE ? Boolean.FALSE
									: Boolean.TRUE);
		} else {
			// XXX is ASC by default a good idea?
			getSelector().setAscending(Boolean.TRUE);
		}
		getSelector().setSortColumn(sortColumn);
		dataModel = null;
		return "";
	}
}
