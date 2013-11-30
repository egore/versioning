package de.egore911.versioning.util.listener;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import de.egore911.versioning.persistence.dao.RoleDao;
import de.egore911.versioning.persistence.dao.UserDao;
import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.Role;
import de.egore911.versioning.persistence.model.User;
import de.egore911.versioning.util.EntityManagerUtil;
import de.egore911.versioning.util.UserUtil;

public class StartupListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("versioning");
		try {
			EntityManager em = emf.createEntityManager();
			try {
				EntityManagerUtil.setEntityManager(em);
				UserDao userDao = new UserDao();
				long count = userDao.count();
				if (count == 0) {
					RoleDao roleDao = new RoleDao();
					List<Role> rolesWithUserAdminPermission = roleDao
							.withPermission(Permission.ADMIN_USERS);
					Role role;
					if (rolesWithUserAdminPermission.isEmpty()) {
						role = new Role();
						role.setName("User Administration");
						role.getPermissions().add(Permission.ADMIN_USERS);
						role = roleDao.save(role);
					} else {
						role = rolesWithUserAdminPermission.get(0);
					}

					User user = new User();
					user.setName("Default admin");
					user.setLogin("admin");
					user.setPassword(new UserUtil().hashPassword("admin"));
					user.setEmail("dev-null@localhost");
					user.getRoles().add(role);
					userDao.save(user);
				}
			} finally {
				EntityManagerUtil.clearEntityManager();
				em.close();
			}
		} finally {
			emf.close();
		}

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// Nothing
	}

}
