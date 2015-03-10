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
package de.egore911.versioning.ui.model;

import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.ExtendedDataModel;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;

import de.egore911.versioning.persistence.model.IntegerDbObject;
import de.egore911.versioning.persistence.selector.AbstractSelector;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class PagingDataModel<T extends IntegerDbObject> extends
		ExtendedDataModel<T> implements Serializable {

	private static final long serialVersionUID = -5559611813725395089L;

	private List<T> dataList;
	private final long count;
	private final AbstractSelector<T> selector;
	private Integer rowKey;

	public PagingDataModel(long count, AbstractSelector<T> selector) {
		this.count = count;
		this.selector = selector;
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
		selector.setOffset(sequenceRange.getFirstRow());
		selector.setLimit(sequenceRange.getRows());
		dataList = selector.findAll();
		for (int index = 0; index < dataList.size(); index++) {
			visitor.process(context, index, argument);
		}
	}

}