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
package de.egore911.versioning.persistence.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@Entity
@Table(name = "action_replacement")
public class ActionReplacement extends AbstractAction {

	private static final long serialVersionUID = 3366431865064864620L;

	private List<Wildcard> wildcards = new ArrayList<>(0);
	private List<Replacement> replacements = new ArrayList<>(0);
	private List<Replacementfile> replacementFiles = new ArrayList<>(0);

	@OneToMany(mappedBy = "actionReplacement", cascade = CascadeType.ALL)
	@OrderBy("value")
	public List<Wildcard> getWildcards() {
		return wildcards;
	}

	public void setWildcards(List<Wildcard> wildcards) {
		this.wildcards = wildcards;
	}

	@OneToMany(mappedBy = "actionReplacement", cascade = CascadeType.ALL)
	@OrderBy("variable,value")
	public List<Replacement> getReplacements() {
		return replacements;
	}

	public void setReplacements(List<Replacement> replacements) {
		this.replacements = replacements;
	}

	@OneToMany(mappedBy = "actionReplacement", cascade = CascadeType.ALL)
	@OrderBy("value")
	public List<Replacementfile> getReplacementFiles() {
		return replacementFiles;
	}

	public void setReplacementFiles(List<Replacementfile> replacementFiles) {
		this.replacementFiles = replacementFiles;
	}

}
