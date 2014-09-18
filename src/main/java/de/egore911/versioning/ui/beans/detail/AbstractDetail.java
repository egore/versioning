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
package de.egore911.versioning.ui.beans.detail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.deltaspike.security.api.authorization.Secured;

import de.egore911.versioning.cdi.RoleAccessDecisionVoter;
import de.egore911.versioning.persistence.dao.AbstractDao;
import de.egore911.versioning.persistence.model.IntegerDbObject;
import de.egore911.versioning.util.SessionUtil;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@SessionScoped
@Secured({RoleAccessDecisionVoter.class})
public abstract class AbstractDetail<T extends IntegerDbObject> implements Serializable {

	private static final long serialVersionUID = -6467847796483360650L;

	private T instance;

	private List<IntegerDbObject> deletions;

	public T getInstance() {
		// No request cache yet, determine it
		if (instance == null || notSameId()) {
			if (FacesContext.getCurrentInstance().getExternalContext()
					.getRequestParameterMap().get("id") != null) {
				instance = load();
			}
			if (instance == null) {
				instance = createEmpty();
			}
		}
		return instance;
	}

	private boolean notSameId() {
		Integer intId = null;
		try {
			intId = Integer.valueOf(FacesContext.getCurrentInstance()
					.getExternalContext().getRequestParameterMap().get("id"));
		} catch (NumberFormatException | NullPointerException e) {
			// Nothing
		}
		return ObjectUtils.notEqual(instance.getId(), intId);
	}

	public void setInstance(T instance) {
		this.instance = instance;
	}

	protected T load() {
		return getDao().findById(
				Integer.valueOf(FacesContext.getCurrentInstance()
						.getExternalContext().getRequestParameterMap()
						.get("id")));
	}

	public boolean isManaged() {
		return getInstance() != null && getInstance().getId() != null;
	}

	protected abstract AbstractDao<T> getDao();

	protected abstract T createEmpty();

	protected boolean validate(String prefix) {
		ValidatorFactory validatorFactory = Validation
				.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<T>> constraintVioalations = validator
				.validate(getInstance());
		if (!constraintVioalations.isEmpty()) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ResourceBundle bundle = SessionUtil.getBundle();
			for (ConstraintViolation<T> constraintVioalation : constraintVioalations) {

				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, bundle.getString(prefix
								+ "_" + constraintVioalation.getPropertyPath())
								+ ": " + constraintVioalation.getMessage(), "");
				facesContext.addMessage("main:" + prefix + "_"
						+ constraintVioalation.getPropertyPath(), message);
			}
			return false;
		}
		return true;
	}

	protected <U extends IntegerDbObject> void markDelete(U entity) {
		getDeletions().add(entity);
	}

	protected List<IntegerDbObject> getDeletions() {
		if (deletions == null) {
			deletions = new ArrayList<>();
		}
		return deletions;
	}

}
