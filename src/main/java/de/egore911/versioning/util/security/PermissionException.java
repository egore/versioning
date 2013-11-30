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
package de.egore911.versioning.util.security;

import de.egore911.versioning.persistence.model.Permission;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class PermissionException extends RuntimeException {

	private static final long serialVersionUID = 8303892206944255550L;

	public PermissionException(Permission permission) {
		super("Missing permission: " + permission);
	}

	public PermissionException() {
		super("Not logged in");
	}

}
