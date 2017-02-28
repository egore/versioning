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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egore911.persistence.util.EntityManagerUtil;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class EntityManagerFilter implements Filter {

	private static final Logger LOG = LoggerFactory
			.getLogger(EntityManagerFilter.class);

	@PersistenceUnit(unitName = "versioning")
	public EntityManagerFactory entityManagerFactory;
	private boolean selfBootstrapped;

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
			entityManager = EntityManagerUtil.getEntityManager();
			if (entityManager != null) {
				EntityManagerUtil.clearEntityManager();
				try {
					entityManager.close();
				} catch (IllegalStateException e) {
					LOG.warn("Could not close entity manager, likely it was closes before");
				}
			}
		}
	}

	@Override
	public void destroy() {
		if (selfBootstrapped && entityManagerFactory != null) {
			entityManagerFactory.close();
		}
	}

}
