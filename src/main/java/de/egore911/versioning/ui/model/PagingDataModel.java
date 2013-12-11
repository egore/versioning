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
package de.egore911.versioning.ui.model;

import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.ExtendedDataModel;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;

import de.egore911.versioning.persistence.dao.AbstractDao;
import de.egore911.versioning.persistence.model.IntegerDbObject;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class PagingDataModel<T extends IntegerDbObject> extends
		ExtendedDataModel<T> implements Serializable {

	private static final long serialVersionUID = -5559611813725395089L;

	private List<T> dataList;
	private final long count;
	private final AbstractDao<T> dao;
	private final String sortColumn;
	private final SortDirection sortDirection;
	private Integer rowKey;

	public PagingDataModel(long count, AbstractDao<T> dao, String sortColumn,
			SortDirection sortDirection) {
		this.count = count;
		this.dao = dao;
		this.sortColumn = sortColumn;
		this.sortDirection = sortDirection;
	}

	@Override
	public void setRowKey(Object o) {
		rowKey = (Integer) o;
	}

	@Override
	public Object getRowKey() {
		return rowKey;
	}

	@Override
	public T getRowData() {
		return dataList.get(rowKey);
	}

	@Override
	public int getRowIndex() {
		return -1;
	}

	@Override
	public Object getWrappedData() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isRowAvailable() {
		return rowKey != null;
	}

	@Override
	public void setRowIndex(int arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void setWrappedData(Object arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getRowCount() {
		return (int) count;
	}

	@Override
	public void walk(FacesContext context, DataVisitor visitor, Range range,
			Object argument) {
		SequenceRange sequenceRange = (SequenceRange) range;
		dataList = dao.findAll(sequenceRange.getFirstRow(),
				sequenceRange.getRows(), sortColumn,
				sortDirection == SortDirection.ASC);
		for (int index = 0; index < dataList.size(); index++) {
			visitor.process(context, index, argument);
		}
	}

}