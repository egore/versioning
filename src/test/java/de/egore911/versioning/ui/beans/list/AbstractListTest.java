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

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import de.egore911.versioning.persistence.model.IntegerDbObject;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class AbstractListTest {

	@Test
	public void testPaging() {
		AbstractList<IntegerDbObject> list = Mockito.mock(AbstractList.class);
		Mockito.doCallRealMethod().when(list).setLimit(Matchers.anyInt());
		Mockito.when(list.getLimit()).thenCallRealMethod();
		Mockito.doCallRealMethod().when(list).setOffset(Matchers.anyInt());
		Mockito.when(list.getOffset()).thenCallRealMethod();
		Mockito.doCallRealMethod().when(list).setPage(Matchers.anyInt());
		Mockito.when(list.getPage()).thenCallRealMethod();

		list.setLimit(10);
		list.setPage(0);
		Assert.assertEquals(0, list.getOffset().intValue());

		list.setPage(1);
		Assert.assertEquals(10, list.getOffset().intValue());

		list.setPage(2);
		Assert.assertEquals(20, list.getOffset().intValue());

		list.setLimit(20);
		Assert.assertEquals(1, list.getPage().intValue());

		list.setLimit(21);
		Assert.assertEquals(0, list.getPage().intValue());

		list.setLimit(19);
		Assert.assertEquals(1, list.getPage().intValue());
	}
}
