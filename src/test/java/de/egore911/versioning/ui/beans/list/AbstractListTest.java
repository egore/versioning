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

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import de.egore911.versioning.persistence.model.IntegerDbObject;
import de.egore911.versioning.persistence.selector.AbstractSelector;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class AbstractListTest {

	@Test
	public void testPaging() {
		AbstractSelector<IntegerDbObject> state = Mockito
				.mock(AbstractSelector.class);
		Mockito.doCallRealMethod().when(state).setLimit(Matchers.anyInt());
		Mockito.when(state.getLimit()).thenCallRealMethod();
		Mockito.doCallRealMethod().when(state).setOffset(Matchers.anyInt());
		Mockito.when(state.getOffset()).thenCallRealMethod();

		AbstractList<IntegerDbObject> list = Mockito.mock(AbstractList.class);
		Mockito.when(list.getSelector()).thenReturn(state);

		Mockito.doCallRealMethod().when(list).setPage(Matchers.anyInt());
		Mockito.when(list.getPage()).thenCallRealMethod();

		state.setLimit(10);
		list.setPage(1);
		Assert.assertEquals(0, state.getOffset().intValue());

		list.setPage(2);
		Assert.assertEquals(10, state.getOffset().intValue());

		list.setPage(3);
		Assert.assertEquals(20, state.getOffset().intValue());

		state.setLimit(20);
		Assert.assertEquals(2, list.getPage().intValue());

		state.setLimit(21);
		Assert.assertEquals(1, list.getPage().intValue());

		state.setLimit(19);
		Assert.assertEquals(2, list.getPage().intValue());
	}
}
