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
package de.egore911.versioning.ui.filter;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import de.egore911.versioning.util.EntityManagerUtil;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class EntityManagerFilter implements Filter {

	@PersistenceUnit(unitName = "versioning")
	public EntityManagerFactory entityManagerFactory;
	private boolean selfBootstrapped = false;

	@Override
	public void init(FilterConfig filterConfig) {
		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence
					.createEntityManagerFactory("versioning");
			selfBootstrapped = true;
		}
	}

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		EntityManager entityManager = entityManagerFactory
				.createEntityManager();
		try {
			EntityManagerUtil.setEntityManager(entityManager);
			filterChain.doFilter(servletRequest, servletResponse);
		} finally {
			EntityManagerUtil.clearEntityManager();
			entityManager.close();
		}
	}

	@Override
	public void destroy() {
		if (selfBootstrapped && entityManagerFactory != null) {
			entityManagerFactory.close();
		}
	}

}
