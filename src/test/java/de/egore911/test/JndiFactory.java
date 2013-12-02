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
package de.egore911.test;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

import org.hsqldb.jdbc.JDBCDataSource;
import org.mockito.Mockito;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class JndiFactory implements InitialContextFactory {
	@Override
	public Context getInitialContext(Hashtable<?, ?> environment)
			throws NamingException {

		JDBCDataSource jdbcDataSource = new JDBCDataSource();
		jdbcDataSource.setUrl("jdbc:hsqldb:mem:testdb");
		jdbcDataSource.setUser("sa");
		jdbcDataSource.setPassword("");

		Name name = Mockito.mock(Name.class);

		NameParser nameParser = Mockito.mock(NameParser.class);
		Mockito.when(nameParser.parse("java:comp/env/jdbc/versioningDS"))
				.thenReturn(name);

		Context context = Mockito.mock(Context.class);
		Mockito.when(context.getNameParser("")).thenReturn(nameParser);
		Mockito.when(context.lookup(name)).thenReturn(jdbcDataSource);

		return context;
	}
}
