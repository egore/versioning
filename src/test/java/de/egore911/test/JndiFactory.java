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
	private static final String DATASOURCE_NAME = "java:/comp/env/jdbc/versioningDS";

	@Override
	public Context getInitialContext(Hashtable<?, ?> environment)
			throws NamingException {

		// HSQLDB
		final JDBCDataSource jdbcDataSource = new JDBCDataSource();
		jdbcDataSource.setUrl("jdbc:hsqldb:mem:testdb");
		jdbcDataSource.setUser("sa");
		jdbcDataSource.setPassword("");

		// MySQL
		// final MysqlDataSource jdbcDataSource = new MysqlDataSource();
		// jdbcDataSource.setUrl("jdbc:mysql://localhost/versioning");
		// jdbcDataSource.setUser("versioning");
		// jdbcDataSource.setPassword("versioning");

		// PostgreSQL
		// final PGSimpleDataSource jdbcDataSource = new PGSimpleDataSource();
		// try {
		// jdbcDataSource.setUrl("jdbc:postgresql://localhost/versioning");
		// } catch (SQLException e) {
		// NamingException ex = new NamingException(e.getMessage());
		// ex.setRootCause(e);
		// throw ex;
		// }
		// jdbcDataSource.setUser("versioning");
		// jdbcDataSource.setPassword("versioning");

		final Name name = Mockito.mock(Name.class);

		NameParser nameParser = Mockito.mock(NameParser.class);
		Mockito.when(nameParser.parse(DATASOURCE_NAME)).thenReturn(name);

		Context context = Mockito.mock(Context.class);
		Mockito.when(context.getNameParser("")).thenReturn(nameParser);
		Mockito.when(context.lookup(Mockito.any(Name.class))).then(
				invocation -> {
					if (invocation.getArguments()[0] == name) {
						return jdbcDataSource;
					}
					return null;
				});
		Mockito.when(context.lookup(Mockito.anyString())).then(
				invocation -> {
					if (DATASOURCE_NAME.equals(invocation.getArguments()[0])) {
						return jdbcDataSource;
					}
					return null;
				});

		return context;
	}
}
