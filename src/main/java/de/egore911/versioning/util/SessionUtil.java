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

import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import de.egore911.versioning.persistence.model.User;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class SessionUtil {

	public static HttpSession getSession() {
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

	public static User getLoggedInUser() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (facesContext != null) {
			ExternalContext externalContext = facesContext.getExternalContext();
			if (externalContext != null) {
				Object session = externalContext.getSession(false);
				if (session instanceof HttpSession) {
					HttpSession httpSession = (HttpSession) session;
					User user = (User) httpSession.getAttribute("user");
					return user;
				}
			}
		}
		return null;
	}

	public static void setLoggedInUser(User user) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (facesContext != null) {
			ExternalContext externalContext = facesContext.getExternalContext();
			if (externalContext != null) {
				Object session = externalContext.getSession(false);
				if (session instanceof HttpSession) {
					HttpSession httpSession = (HttpSession) session;
					httpSession.setAttribute("user", user);
					// Keep the session for eight hours once logged in
					httpSession.setMaxInactiveInterval(28800);
				}
			}
		}
	}

	public static ResourceBundle getBundle() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Locale locale = facesContext.getViewRoot().getLocale();
		return ResourceBundle.getBundle("messages", locale);
	}
}
