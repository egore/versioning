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
package de.egore911.versioning.util.vcs;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egore911.versioning.persistence.dao.VersionDao;
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
		Future<Boolean> future = executor.submit(() -> tagExistsImpl(project, tagName));
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
		Future<List<Tag>> future = executor.submit(() -> getTagsImpl(project));
		try {
			List<Tag> tags = future.get(10, TimeUnit.SECONDS);
			Collections.sort(tags);
			Collections.reverse(tags);
			tags = tags.subList(0, Math.min(tags.size(), 20));
			VersionDao versionDao = new VersionDao();
			for (Tag tag : tags) {
				tag.setExists(versionDao.tagExists(project, tag.getName()));
			}
			return tags;
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
		private Boolean exists;

		public Tag(Date date, String name) {
			this.date = date;
			this.name = name;
		}

		public void setExists(Boolean exists) {
			this.exists = exists;
		}

		public Boolean getExists() {
			return exists;
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
