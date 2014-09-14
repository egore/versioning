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

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.TagTransformer;
import de.egore911.versioning.persistence.model.TagTransformer_;
import de.egore911.versioning.persistence.selector.AbstractSelector;
import de.egore911.versioning.persistence.selector.TagTransformerSelector;
import de.egore911.versioning.util.security.RequiresPermission;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@Named
@RequestScoped
@RequiresPermission(Permission.ADMIN_SETTINGS)
public class TagTransformerList extends AbstractList<TagTransformer> {

	@Inject
	@SessionScoped
	private TagTransformerSelector selector;

	@Override
	public TagTransformerSelector getSelector() {
		return selector;
	}

	@Override
	public void setSelector(AbstractSelector<TagTransformer> selector) {
		this.selector = (TagTransformerSelector) selector;
	}

	@PostConstruct
	public void postConstruct() {
		if (selector.getSortColumn() == null) {
			selector.setSortColumn(TagTransformer_.name.getName());
			selector.setAscending(Boolean.TRUE);
			selector.setLimit(DEFAULT_LIMIT);
		}
	}

}