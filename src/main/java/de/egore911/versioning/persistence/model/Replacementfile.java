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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@Entity
@Table(name = "replacementfile")
public class Replacementfile extends IntegerDbObject {

	private static final long serialVersionUID = -4464568512198734897L;

	private ActionReplacement actionReplacement;

	private String value;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_action_replace", nullable = false)
	@NotNull
	public ActionReplacement getActionReplacement() {
		return actionReplacement;
	}

	public void setActionReplacement(ActionReplacement actionReplacement) {
		this.actionReplacement = actionReplacement;
	}

	@Column(nullable = false, length = 511)
	@NotNull
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}