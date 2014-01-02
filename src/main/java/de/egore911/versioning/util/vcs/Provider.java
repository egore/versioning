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
package de.egore911.versioning.util.vcs;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egore911.versioning.persistence.model.Project;
import de.egore911.versioning.util.VersionUtil;

/**
 * Core infrastructure for VCS information providers.
 * 
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public abstract class Provider {

	private static final Logger log = LoggerFactory.getLogger(Provider.class);

	/**
	 * Checks if a given tag exists. If this will take more than 10 seconds, it
	 * will be aborted and the tag is reported not to exist.
	 * 
	 * @param project
	 *            the project that has to have the tag
	 * @param tagName
	 *            the name of the tag
	 * @return <code>true</code>, if the tag exists
	 */
	public boolean tagExists(final Project project, final String tagName) {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<Boolean> future = executor.submit(new Callable<Boolean>() {
			@Override
			public Boolean call() {
				return tagExistsImpl(project, tagName);
			}
		});
		try {
			return future.get(10, TimeUnit.SECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			log.error(e.getMessage(), e);
			return false;
		} finally {
			executor.shutdownNow();
		}
	}

	/**
	 * Checks if a given tag exists.
	 * 
	 * @param project
	 *            the project that has to have the tag
	 * @param tagName
	 *            the name of the tag
	 * @return <code>true</code>, if the tag exists
	 */
	protected abstract boolean tagExistsImpl(Project project, String tagName);

	public List<Tag> getTags(final Project project) {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<List<Tag>> future = executor.submit(new Callable<List<Tag>>() {
			@Override
			public List<Tag> call() {
				List<Tag> tags = getTagsImpl(project);
				Collections.sort(tags);
				Collections.reverse(tags);
				return tags;
			}
		});
		try {
			return future.get(10, TimeUnit.SECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			log.error(e.getMessage(), e);
			return Collections.emptyList();
		} finally {
			executor.shutdownNow();
		}
	}

	protected abstract List<Tag> getTagsImpl(Project project);

	public static class Tag implements Serializable, Comparable<Tag> {

		private static final long serialVersionUID = 7107245776731571047L;

		private final Date date;
		private final String name;

		public Tag(Date date, String name) {
			this.date = date;
			this.name = name;
		}

		public Date getDate() {
			return date;
		}

		public String getName() {
			return name;
		}

		@Override
		public int compareTo(Tag o) {
			if (this.getDate() != null && o.getDate() != null) {
				return this.getDate().compareTo(o.getDate());
			}
			try {
				return VersionUtil.isNewerThan(this.getName(), o.getName()) ? 1
						: (VersionUtil.isNewerThan(o.getName(), this.getName()) ? -1
								: 0);
			} catch (Exception e) {
				return this.getName().compareTo(o.getName());
			}
		}

		@Override
		public String toString() {
			return name;
		}

	}
}
