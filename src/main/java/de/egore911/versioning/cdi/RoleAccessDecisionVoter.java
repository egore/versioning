package de.egore911.versioning.cdi;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;

import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.apache.deltaspike.security.api.authorization.AccessDecisionVoter;
import org.apache.deltaspike.security.api.authorization.AccessDecisionVoterContext;
import org.apache.deltaspike.security.api.authorization.SecurityViolation;
import org.hibernate.Hibernate;

import de.egore911.versioning.persistence.dao.UserDao;
import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.Role;
import de.egore911.versioning.persistence.model.User;
import de.egore911.versioning.util.SessionUtil;
import de.egore911.versioning.util.security.PermissionException;
import de.egore911.versioning.util.security.RequiresPermission;

@ApplicationScoped
public class RoleAccessDecisionVoter implements AccessDecisionVoter {
	private static final long serialVersionUID = -8007511215776345835L;

	public Set<SecurityViolation> checkPermission(
			AccessDecisionVoterContext voterContext) {
		final RequiresPermission requiresPermission = voterContext.getMetaDataFor(
				RequiresPermission.class.getName(), RequiresPermission.class);
		if (requiresPermission != null && requiresPermission.value().length > 0) {
			User user = SessionUtil.getLoggedInUser();
			if (user == null) {
				throw new PermissionException();
			}
			user = reattachUser(user);
			for (Permission permission : requiresPermission.value()) {
				if (user.hasPermission(permission)) {
					return Collections.emptySet();
				}
			}
			SecurityViolation o = new SecurityViolation() {
				private static final long serialVersionUID = -9113170129272374127L;

				@Override
				public String getReason() {
					return Arrays.toString(requiresPermission.value());
				}
			};
			return Collections.singleton(o);
		} else {
			return Collections.emptySet();
		}
	}

	private User reattachUser(User user) {
		user = BeanProvider.getContextualReference(UserDao.class)
				.reattach(user);
		for (Role role : user.getRoles()) {
			Hibernate.initialize(role.getPermissions());
		}
		SessionUtil.setLoggedInUser(user);
		return user;
	}

}