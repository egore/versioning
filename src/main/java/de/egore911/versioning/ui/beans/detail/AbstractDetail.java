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
package de.egore911.versioning.ui.beans.detail;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import de.egore911.persistence.dao.AbstractDao;
import de.egore911.versioning.persistence.model.IntegerDbObject;
import de.egore911.versioning.ui.beans.AbstractBean;
import de.egore911.versioning.util.SessionUtil;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public abstract class AbstractDetail<T extends IntegerDbObject> extends
		AbstractBean {

	@ManagedProperty(value = "#{param.id}")
	private Integer id;

	protected T instance;

	public T getInstance() {
		// No request cache yet, determine it
		if (instance == null) {
			HttpSession session = SessionUtil.getSession();
			instance = (T) session.getAttribute(this.getClass().getSimpleName()
					+ "_instance");
			if (instance == null) {
				if (id != null) {
					instance = load();
					session.setAttribute(this.getClass().getSimpleName()
							+ "_deletions", new ArrayList<>());
				}
				if (instance == null) {
					instance = createEmpty();
					session.setAttribute(this.getClass().getSimpleName()
							+ "_deletions", new ArrayList<>());
				}
			}
			session.setAttribute(this.getClass().getSimpleName() + "_instance",
					instance);
		}
		return instance;
	}

	protected T load() {
		return getDao().findById(id);
	}

	public void setInstance(T instance) {
		HttpSession session = SessionUtil.getSession();
		session.setAttribute(this.getClass().getSimpleName() + "_instance",
				instance);
		this.instance = instance;
	}

	public boolean isManaged() {
		return getInstance() != null && getInstance().getId() != null;
	}

	protected abstract AbstractDao<T> getDao();

	protected abstract T createEmpty();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
		// If a ID got passed we were called with a parameter in the URL
		if (id != null) {
			setInstance(null);
		}
	}

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
		List<U> deletions = getDeletions();
		deletions.add(entity);
		HttpSession session = SessionUtil.getSession();
		session.setAttribute(this.getClass().getSimpleName() + "_deletions",
				deletions);
	}

	protected <U extends IntegerDbObject> List<U> getDeletions() {
		HttpSession session = SessionUtil.getSession();
		List<U> deletions = (List<U>) session.getAttribute(this.getClass()
				.getSimpleName() + "_deletions");
		if (deletions == null) {
			deletions = new ArrayList<>();
		}
		return deletions;
	}

}
