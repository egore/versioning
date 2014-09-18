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

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.deltaspike.core.api.config.view.metadata.ViewConfigResolver;
import org.apache.deltaspike.core.api.exception.control.ExceptionHandler;
import org.apache.deltaspike.core.api.exception.control.Handles;
import org.apache.deltaspike.core.api.exception.control.event.ExceptionEvent;
import org.apache.deltaspike.security.api.authorization.ErrorViewAwareAccessDeniedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@ExceptionHandler
public class WrappedExceptionHandler {

	@Inject
	private ViewConfigResolver viewConfigResolver;

	private static final Logger log = LoggerFactory
			.getLogger(WrappedExceptionHandler.class);

	public void onIllegalStateException(
			@Handles ExceptionEvent<ErrorViewAwareAccessDeniedException> e) {

		FacesContext facesContext = FacesContext.getCurrentInstance();

		String viewId = viewConfigResolver
				.getDefaultErrorViewConfigDescriptor().getViewId();
		UIViewRoot viewRoot = facesContext.getApplication().getViewHandler()
				.createView(facesContext, viewId);
		facesContext.setViewRoot(viewRoot);

		/*
		 * for (Iterator<ExceptionQueuedEvent> iter =
		 * getUnhandledExceptionQueuedEvents() .iterator(); iter.hasNext();) {
		 * 
		 * // Determine root cause Throwable t =
		 * iter.next().getContext().getException(); while (t.getCause() != null)
		 * { t = t.getCause(); }
		 * 
		 * // If it was because of permissions, redirect to the login if (t
		 * instanceof PermissionException) { FacesContext facesContext =
		 * FacesContext.getCurrentInstance(); try { ResourceBundle bundle =
		 * SessionUtil.getBundle(); FacesMessage message = new FacesMessage(
		 * FacesMessage.SEVERITY_ERROR, bundle.getString("missing_permission"),
		 * bundle.getString("missing_permission_detail"));
		 * facesContext.addMessage("main:user_login", message);
		 * facesContext.getExternalContext().redirect("login.xhtml");
		 * facesContext.responseComplete(); } catch (IOException e) {
		 * log.error(e.getMessage(), e); } finally { iter.remove(); } } }
		 * wrapped.handle();
		 */
	}
}