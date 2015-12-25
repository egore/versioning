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
package de.egore911.versioning.ui.beans.list;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.model.DataModel;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import de.egore911.persistence.selector.AbstractSelector;
import de.egore911.versioning.persistence.model.IntegerDbObject;
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
			dataModel = new PagingDataModel<>(selector.count(), getSelector());
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
