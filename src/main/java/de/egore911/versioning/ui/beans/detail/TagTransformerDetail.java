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

import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;

import de.egore911.versioning.persistence.dao.TagTransformerDao;
import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.TagTransformer;
import de.egore911.versioning.util.SessionUtil;
import de.egore911.versioning.util.security.RequiresPermission;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@ManagedBean(name = "tagtransformerDetail")
@RequestScoped
@RequiresPermission(Permission.ADMIN_SETTINGS)
public class TagTransformerDetail extends AbstractDetail<TagTransformer> {

	@Override
	protected TagTransformerDao getDao() {
		return new TagTransformerDao();
	}

	@Override
	protected TagTransformer createEmpty() {
		return new TagTransformer();
	}

	public String save() {

		if (!validate("tagtransformer")) {
			return "";
		}

		try {
			Pattern.compile(getInstance().getSearchPattern());
		} catch (PatternSyntaxException e) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ResourceBundle bundle = SessionUtil.getBundle();
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					bundle.getString("invalid_search_pattern"),
					MessageFormat.format(
							bundle.getString("invalid_search_pattern_detail"),
							e.getMessage()));
			facesContext.addMessage("main:server_name", message);
			return "";
		}

		getDao().save(getInstance());
		setInstance(null);
		return "/tagtransformers.xhtml";
	}

	private String testPattern;
	private String testPatternEvaluated;

	public String getTestPattern() {
		return testPattern;
	}

	public void setTestPattern(String testPattern) {
		this.testPattern = testPattern;
		if (StringUtils.isEmpty(testPattern)) {
			return;
		}
		try {
			testPatternEvaluated = testPattern.replaceAll(getInstance()
					.getSearchPattern(), getInstance().getReplacementPattern());
		} catch (PatternSyntaxException e) {
			testPatternEvaluated = e.getMessage();
		}
	}

	public String getTestPatternEvaluated() {
		return testPatternEvaluated;
	}

}
