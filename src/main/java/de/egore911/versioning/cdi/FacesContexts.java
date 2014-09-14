package de.egore911.versioning.cdi;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

public class FacesContexts {

	@Produces
	@RequestScoped
	protected FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

}
