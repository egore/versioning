package de.egore911.versioning.ui.beans;

import javax.annotation.PostConstruct;

import de.egore911.versioning.persistence.dao.UserDao;
import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.User;
import de.egore911.versioning.util.SessionUtil;
import de.egore911.versioning.util.security.PermissionException;
import de.egore911.versioning.util.security.RequiresPermission;

public class AbstractBean {

	@PostConstruct
	public void created() {
		RequiresPermission requiresPermission = this.getClass().getAnnotation(
				RequiresPermission.class);
		if (requiresPermission != null) {
			SessionUtil sessionUtil = new SessionUtil();
			User user = sessionUtil.getLoggedInUser();
			if (user == null) {
				throw new PermissionException();
			}
			user = new UserDao().reattach(user);
			sessionUtil.setLoggedInUser(user);
			for (Permission permission : requiresPermission.value()) {
				if (!user.hasPermission(permission)) {
					throw new PermissionException(permission);
				}
			}
		}
	}

}
