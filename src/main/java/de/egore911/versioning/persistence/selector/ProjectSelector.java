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
package de.egore911.versioning.persistence.selector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import de.egore911.persistence.selector.AbstractSelector;
import de.egore911.versioning.persistence.model.Project;
import de.egore911.versioning.persistence.model.Project_;
import de.egore911.versioning.persistence.model.Server;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class ProjectSelector extends AbstractSelector<Project> {

	private static final long serialVersionUID = 6585242967556404330L;

	private Server configuredForServer;

	private Boolean excludeDeleted;

	@Override
	protected Class<Project> getEntityClass() {
		return Project.class;
	}

	@Override
	protected List<Predicate> generatePredicateList(CriteriaBuilder builder,
			Root<Project> from) {
		List<Predicate> predicates = new ArrayList<>();

		if (configuredForServer != null) {
			ListJoin<Project, Server> fromServer = from
					.join(Project_.configuredServers);
			predicates.add(fromServer.in(configuredForServer));
		}

		if (Boolean.TRUE.equals(excludeDeleted)) {
			predicates.add(builder.notEqual(from.get(Project_.deleted), Boolean.TRUE));
		}

		return predicates;
	}

	@Override
	protected List<Order> getDefaultOrderList(CriteriaBuilder builder,
			Root<Project> from) {
		return Collections.singletonList(builder.asc(from.get(Project_.name)));
	}

	public Server getConfiguredForServer() {
		return configuredForServer;
	}

	public void setConfiguredForServer(Server configuredForServer) {
		this.configuredForServer = configuredForServer;
	}

	public ProjectSelector setExcludeDeleted(Boolean excludeDeleted) {
		this.excludeDeleted = excludeDeleted;
		return this;
	}

}
