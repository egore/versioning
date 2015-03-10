/*
 * Copyright 2013  Christoph Brill <egore911@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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

	private SessionUtil() {
	}

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
