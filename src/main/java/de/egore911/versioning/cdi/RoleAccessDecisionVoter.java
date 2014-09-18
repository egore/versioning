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
import de.egore911.versioning.util.security.RequiresPermission;

@ApplicationScoped
public class RoleAccessDecisionVoter implements AccessDecisionVoter {

	private static final long serialVersionUID = -8007511215776345835L;

	private static class StringSecurityViolation implements SecurityViolation {

		private static final long serialVersionUID = -9113170129272374127L;

		private final String string;

		public StringSecurityViolation(String string) {
			this.string = string;
		}

		@Override
		public String getReason() {
			return string;
		}

	}

	public Set<SecurityViolation> checkPermission(
			AccessDecisionVoterContext voterContext) {
		final RequiresPermission requiresPermission = voterContext
				.getMetaDataFor(RequiresPermission.class.getName(),
						RequiresPermission.class);

		// If no permission is required, abort checking
		if (requiresPermission == null) {
			return Collections.emptySet();
		}

		// If any permission is given, a user must be logged in at least
		User user = SessionUtil.getLoggedInUser();
		if (user == null) {
			return Collections
					.singleton((SecurityViolation) new StringSecurityViolation(
							"Not logged in")); // TODO translatable
		}

		// No permission required, we're done here
		if (requiresPermission.value().length == 0) {
			return Collections.emptySet();
		}

		// Check if the user has any of the permissions requested
		user = reattachUser(user);
		for (Permission permission : requiresPermission.value()) {
			if (user.hasPermission(permission)) {
				return Collections.emptySet();
			}
		}

		// No match found, bail out
		return Collections
				.singleton((SecurityViolation) new StringSecurityViolation(
						Arrays.toString(requiresPermission.value())));
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