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

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.model.DataModel;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import de.egore911.versioning.persistence.dao.AbstractDao;
import de.egore911.versioning.persistence.model.IntegerDbObject;
import de.egore911.versioning.ui.beans.AbstractBean;
import de.egore911.versioning.ui.model.PagingDataModel;
import de.egore911.versioning.ui.model.SortDirection;
import de.egore911.versioning.util.SessionUtil;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public abstract class AbstractList<T extends IntegerDbObject> extends
		AbstractBean {

	private static final int DEFAULT_LIMIT = 20;

	public static class State implements Serializable {

		private static final long serialVersionUID = -1222436414575029583L;

		private Integer offset;
		private Integer limit;
		private String sortColumn;
		private SortDirection sortDirection;

		public Integer getOffset() {
			if (offset == null) {
				return 0;
			}
			return offset;
		}

		public void setOffset(Integer offset) {
			this.offset = offset;
		}

		public Integer getLimit() {
			if (limit == null) {
				limit = DEFAULT_LIMIT;
			}
			return limit;
		}

		public void setLimit(Integer limit) {
			this.limit = limit;
		}

		public String getSortColumn() {
			return sortColumn;
		}

		public void setSortColumn(String sortColumn) {
			this.sortColumn = sortColumn;
		}

		public SortDirection getSortDirection() {
			return sortDirection;
		}

		public void setSortDirection(SortDirection sortDirection) {
			this.sortDirection = sortDirection;
		}

	}

	private transient State state = createInitialState();
	private transient HttpSession session;

	protected abstract State createInitialState();

	@Override
	@PostConstruct
	public void postConstruct() {
		super.postConstruct();
		session = SessionUtil.getSession();
		State previousState = (State) session.getAttribute(this.getClass()
				.getSimpleName() + "_state");
		if (previousState != null) {
			setState(previousState);
		}
	}

	@PreDestroy
	public void preDestroy() {
		session.setAttribute(this.getClass().getSimpleName() + "_state",
				getState());
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public long count() {
		return getDao().count();
	}

	public Integer getPage() {
		return (getState().getOffset() / getState().getLimit()) + 1;
	}

	public void setPage(Integer page) {
		if (page == null) {
			return;
		}
		getState().setOffset((page - 1) * getState().getLimit());
	}

	public Integer getMaxPages() {
		return (int) Math.ceil((double) count() / getState().getLimit());
	}

	protected abstract AbstractDao<T> getDao();

	private DataModel<T> dataModel;

	public DataModel<T> getDataModel() {
		if (dataModel == null) {
			AbstractDao<T> dao = getDao();
			dataModel = new PagingDataModel<>(dao.count(), dao, getState()
					.getSortColumn(), getState().getSortDirection());
		}
		return dataModel;
	}

	public String orderBy(String sortColumn) {
		if (StringUtils.equals(getState().getSortColumn(), sortColumn)) {
			getState()
					.setSortDirection(
							getState().getSortDirection() == SortDirection.ASC ? SortDirection.DESC
									: SortDirection.ASC);
		} else {
			// XXX is ASC by default a good idea?
			getState().setSortDirection(SortDirection.ASC);
		}
		getState().setSortColumn(sortColumn);
		dataModel = null;
		return "";
	}
}
