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
package de.egore911.versioning.util;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.User;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class SessionUtil {

	public static class LoggedInUser implements Serializable {

		private static final long serialVersionUID = -7077049038648586713L;

		private String name;
		private Integer id;
		private Set<Permission> permissions;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Set<Permission> getPermissions() {
			return permissions;
		}

		public void setPermissions(Set<Permission> permissions) {
			this.permissions = permissions;
		}

		public boolean hasPermission(Permission permission) {
			return this.permissions.contains(permission);
		}
	}

	public HttpSession getSession() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (facesContext != null) {
			ExternalContext externalContext = facesContext.getExternalContext();
			if (externalContext != null) {
				Object session = externalContext.getSession(false);
				if (session instanceof HttpSession) {
					return (HttpSession) session;
				}
			}
		}
		return null;
	}

	public LoggedInUser getLoggedInUser() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (facesContext != null) {
			ExternalContext externalContext = facesContext.getExternalContext();
			if (externalContext != null) {
				Object session = externalContext.getSession(false);
				if (session instanceof HttpSession) {
					HttpSession httpSession = (HttpSession) session;
					LoggedInUser loggedInUser = (LoggedInUser) httpSession
							.getAttribute("user");
					return loggedInUser;
				}
			}
		}
		return null;
	}

	public void setLoggedInUser(User user) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (facesContext != null) {
			ExternalContext externalContext = facesContext.getExternalContext();
			if (externalContext != null) {
				Object session = externalContext.getSession(false);
				if (session instanceof HttpSession) {
					HttpSession httpSession = (HttpSession) session;
					LoggedInUser loggedInUser = new LoggedInUser();
					loggedInUser.setName(user.getName());
					loggedInUser.setId(user.getId());
					loggedInUser.setPermissions(user.getPermissions());
					httpSession.setAttribute("user", loggedInUser);
				}
			}
		}
	}

	public ResourceBundle getBundle() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Locale locale = facesContext.getViewRoot().getLocale();
		return ResourceBundle.getBundle("messages", locale);
	}
}
