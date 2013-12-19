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

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.model.DataModel;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import de.egore911.versioning.persistence.dao.AbstractDao;
import de.egore911.versioning.persistence.model.IntegerDbObject;
import de.egore911.versioning.persistence.selector.AbstractSelector;
import de.egore911.versioning.ui.beans.AbstractBean;
import de.egore911.versioning.ui.model.PagingDataModel;
import de.egore911.versioning.util.SessionUtil;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public abstract class AbstractList<T extends IntegerDbObject> extends
		AbstractBean {

	protected static final int DEFAULT_LIMIT = 20;

	private transient AbstractSelector<T> selector = createInitialSelector();
	private transient HttpSession session;

	protected abstract AbstractSelector<T> createInitialSelector();

	@Override
	@PostConstruct
	public void postConstruct() {
		super.postConstruct();
		session = SessionUtil.getSession();
		AbstractSelector<T> previousState = (AbstractSelector<T>) session
				.getAttribute(this.getClass().getSimpleName() + "_selector");
		if (previousState != null) {
			setSelector(previousState);
		}
	}

	@PreDestroy
	public void preDestroy() {
		session.setAttribute(this.getClass().getSimpleName() + "_selector",
				getSelector());
	}

	public AbstractSelector<T> getSelector() {
		return selector;
	}

	public void setSelector(AbstractSelector<T> selector) {
		this.selector = selector;
	}

	public long count() {
		return getDao().count();
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

	protected abstract AbstractDao<T> getDao();

	private DataModel<T> dataModel;

	public DataModel<T> getDataModel() {
		if (dataModel == null) {
			AbstractDao<T> dao = getDao();
			dataModel = new PagingDataModel<>(dao.count(), getSelector());
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
