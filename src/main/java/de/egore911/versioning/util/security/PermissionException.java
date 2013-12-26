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

import java.util.Arrays;

import de.egore911.versioning.persistence.model.Permission;

/**
 * Indicates that a user was not allowed to perform an operation because he did
 * not have any of the requested permissions or was not logged in at all.
 * 
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class PermissionException extends RuntimeException {

	private static final long serialVersionUID = 8303892206944255550L;

	/**
	 * The user was missing any of the given permissions.
	 * 
	 * @param permissions
	 *            list of permissions
	 */
	public PermissionException(Permission[] permissions) {
		super("Missing any of the permission: " + Arrays.toString(permissions));
	}

	/**
	 * The user was not logged in.
	 */
	public PermissionException() {
		super("Not logged in");
	}

}
