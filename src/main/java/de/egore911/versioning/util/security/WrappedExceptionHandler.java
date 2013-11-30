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

import java.io.IOException;
import java.util.Iterator;
import java.util.ResourceBundle;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egore911.versioning.util.SessionUtil;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class WrappedExceptionHandler extends ExceptionHandlerWrapper {

	private static final Logger log = LoggerFactory
			.getLogger(WrappedExceptionHandler.class);

	private final ExceptionHandler wrapped;

	public WrappedExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return this.wrapped;
	}

	@Override
	public void handle() throws FacesException {
		for (Iterator<ExceptionQueuedEvent> iter = getUnhandledExceptionQueuedEvents()
				.iterator(); iter.hasNext();) {

			// Determine root cause
			Throwable t = iter.next().getContext().getException();
			while (t.getCause() != null) {
				t = t.getCause();
			}

			// If it was because of permissions, redirect to the login
			if (t instanceof PermissionException) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				try {
					SessionUtil sessionUtil = new SessionUtil();
					ResourceBundle bundle = sessionUtil.getBundle();
					FacesMessage message = new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							bundle.getString("missing_permission"),
							bundle.getString("missing_permission_detail"));
					facesContext.addMessage("main:user_login", message);
					facesContext.getExternalContext().redirect("login.xhtml");
					facesContext.responseComplete();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				} finally {
					iter.remove();
				}
			}
		}
		wrapped.handle();
	}
}