package de.egore911.versioning.ui.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import de.egore911.versioning.persistence.model.Permission;

@FacesConverter("permissionConverter")
public class PermissionConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		try {
			return Permission.valueOf(value);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		Permission permission = (Permission) value;
		return permission != null ? permission.name() : null;
	}

}
