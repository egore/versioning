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
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import de.egore911.versioning.persistence.dao.MavenRepositoryDao;
import de.egore911.versioning.persistence.dao.ProjectDao;
import de.egore911.versioning.persistence.dao.ServerDao;
import de.egore911.versioning.persistence.dao.TagTransformerDao;
import de.egore911.versioning.persistence.dao.VcshostDao;
import de.egore911.versioning.persistence.model.ActionCopy;
import de.egore911.versioning.persistence.model.ActionExtraction;
import de.egore911.versioning.persistence.model.Extraction;
import de.egore911.versioning.persistence.model.MavenArtifact;
import de.egore911.versioning.persistence.model.MavenRepository;
import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.Project;
import de.egore911.versioning.persistence.model.Server;
import de.egore911.versioning.persistence.model.SpacerUrl;
import de.egore911.versioning.persistence.model.TagTransformer;
import de.egore911.versioning.persistence.model.Variable;
import de.egore911.versioning.persistence.model.VcsHost;
import de.egore911.versioning.util.security.RequiresPermission;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@ManagedBean(name = "projectDetail")
@RequestScoped
@RequiresPermission(Permission.ADMIN_SETTINGS)
public class ProjectDetail extends AbstractDetail<Project> {

	@Override
	protected ProjectDao getDao() {
		return new ProjectDao();
	}

	@Override
	protected Project createEmpty() {
		return new Project();
	}

	public SelectItem[] getVcshostSelectItems() {
		List<VcsHost> vcshosts = new VcshostDao().findAll();
		SelectItem[] items = new SelectItem[vcshosts.size()];
		int i = 0;
		for (VcsHost vcshost : vcshosts) {
			items[i++] = new SelectItem(vcshost, vcshost.getName());
		}
		return items;
	}

	public SelectItem[] getAllServerSelectItems() {
		List<Server> servers = new ServerDao().findAll();
		SelectItem[] items = new SelectItem[servers.size()];
		int i = 0;
		for (Server server : servers) {
			items[i++] = new SelectItem(server, server.getName());
		}
		return items;
	}

	public SelectItem[] getAllMavenRepositoriesSelectItems() {
		List<MavenRepository> mavenRepositories = new MavenRepositoryDao()
				.findAll();
		SelectItem[] items = new SelectItem[mavenRepositories.size()];
		int i = 0;
		for (MavenRepository mavenRepository : mavenRepositories) {
			items[i++] = new SelectItem(mavenRepository,
					mavenRepository.getName());
		}
		return items;
	}

	public SelectItem[] getAllTagTransformerSelectItems() {
		List<TagTransformer> tagTransformers = new TagTransformerDao()
				.findAll();
		SelectItem[] items = new SelectItem[tagTransformers.size()];
		int i = 0;
		for (TagTransformer tagTransformer : tagTransformers) {
			items[i++] = new SelectItem(tagTransformer,
					tagTransformer.getName());
		}
		return items;
	}

	public String chooseCopy() {
		Project project = getInstance();
		ActionCopy actionCopy = new ActionCopy();
		project.getActionCopies().add(actionCopy);
		actionCopy.setProject(project);
		setInstance(project);
		return "";
	}

	public String chooseCopyMavenArtifact(ActionCopy actionCopy) {
		MavenArtifact mavenArtifact = new MavenArtifact();
		actionCopy.setMavenArtifact(mavenArtifact);
		mavenArtifact.setActionCopy(actionCopy);
		setInstance(getInstance());
		return "";
	}

	public String chooseCopySpacerUrl(ActionCopy actionCopy) {
		SpacerUrl spacerUrl = new SpacerUrl();
		actionCopy.setSpacerUrl(spacerUrl);
		spacerUrl.setActionCopy(actionCopy);
		setInstance(getInstance());
		return "";
	}

	public String chooseExtraction() {
		Project project = getInstance();
		ActionExtraction actionExtraction = new ActionExtraction();
		addExtraction(actionExtraction);
		project.getActionExtractions().add(actionExtraction);
		actionExtraction.setProject(project);
		setInstance(project);
		return "";
	}

	public String chooseExtractionMavenArtifact(
			ActionExtraction actionExtraction) {
		MavenArtifact mavenArtifact = new MavenArtifact();
		actionExtraction.setMavenArtifact(mavenArtifact);
		mavenArtifact.setActionExtraction(actionExtraction);
		setInstance(getInstance());
		return "";
	}

	public String chooseExtractionSpacerUrl(ActionExtraction actionExtraction) {
		SpacerUrl spacerUrl = new SpacerUrl();
		actionExtraction.setSpacerUrl(spacerUrl);
		spacerUrl.setActionExtraction(actionExtraction);
		setInstance(getInstance());
		return "";
	}

	public String addExtraction(ActionExtraction actionExtraction) {
		Extraction extraction = new Extraction();
		extraction.setActionExtraction(actionExtraction);
		actionExtraction.getExtractions().add(extraction);
		return "";
	}

	public String save() {

		if (!validate("project")) {
			return "";
		}

		for (ActionCopy actionCopy : getInstance().getActionCopies()) {
			checkVariableExists(actionCopy.getTargetPath());
		}
		for (ActionExtraction actionExtraction : getInstance()
				.getActionExtractions()) {
			for (Extraction extraction : actionExtraction.getExtractions()) {
				checkVariableExists(extraction.getSource());
				checkVariableExists(extraction.getDestination());
			}
		}

		getDao().save(getInstance());
		setInstance(null);
		return "/projects.xhtml";
	}

	private void checkVariableExists(String s) {
		Matcher m = Variable.VARIABLE_PATTERN.matcher(s);
		while (m.find()) {
			String variableName = m.group(1);
			for (Server server : getInstance().getConfiguredServers()) {
				boolean found = false;
				for (Variable variable : server.getVariables()) {
					if (variable.getName().equals(variableName)) {
						found = true;
						break;
					}
				}
				if (!found) {
					FacesContext facesContext = FacesContext
							.getCurrentInstance();
					ResourceBundle bundle = sessionUtil.getBundle();
					FacesMessage message = new FacesMessage(
							FacesMessage.SEVERITY_WARN,
							MessageFormat.format(
									bundle.getString("missing_variable_X_for_server_Y"),
									variableName, server.getName()),
							MessageFormat.format(
									bundle.getString("missing_variable_X_for_server_Y_detail"),
									variableName, server.getName()));
					facesContext.addMessage("main:server_name", message);
				}
			}
		}
	}

}
