package de.egore911.versioning.util.security;

import de.egore911.versioning.persistence.model.Permission;

public class PermissionException extends RuntimeException {

	private static final long serialVersionUID = 8303892206944255550L;

	public PermissionException(Permission permission) {
		super("Missing permission: " + permission);
	}

	public PermissionException() {
		super("Not logged in");
	}

}
