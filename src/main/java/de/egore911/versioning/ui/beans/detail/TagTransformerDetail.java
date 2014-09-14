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

import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.deltaspike.core.api.provider.BeanProvider;

import de.egore911.versioning.persistence.dao.TagTransformerDao;
import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.TagTransformer;
import de.egore911.versioning.util.SessionUtil;
import de.egore911.versioning.util.security.RequiresPermission;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@Named
@RequiresPermission(Permission.ADMIN_SETTINGS)
public class TagTransformerDetail extends AbstractDetail<TagTransformer> {

	private static final long serialVersionUID = 6727124243253519584L;

	@Override
	protected TagTransformerDao getDao() {
		return BeanProvider.getContextualReference(TagTransformerDao.class);
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
