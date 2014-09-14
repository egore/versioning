package de.egore911.versioning.cdi;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

class HttpParams {

	@Inject
	private FacesContext facesContext;

	@Produces
	@HttpParam
	protected String getParamValue(InjectionPoint ip) {

		String name = ip.getAnnotated().getAnnotation(HttpParam.class).value();

		if ("".equals(name)) {
			name = ip.getMember().getName();
		}

		return facesContext.getExternalContext().getRequestParameterMap()
				.get(name);

	}

}