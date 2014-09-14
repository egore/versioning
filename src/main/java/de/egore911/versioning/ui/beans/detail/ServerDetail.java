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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egore911.versioning.persistence.dao.ProjectDao;
import de.egore911.versioning.persistence.dao.ServerDao;
import de.egore911.versioning.persistence.model.ActionReplacement;
import de.egore911.versioning.persistence.model.BinaryData;
import de.egore911.versioning.persistence.model.IntegerDbObject;
import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.Project;
import de.egore911.versioning.persistence.model.Replacement;
import de.egore911.versioning.persistence.model.Replacementfile;
import de.egore911.versioning.persistence.model.Server;
import de.egore911.versioning.persistence.model.Variable;
import de.egore911.versioning.persistence.model.Version;
import de.egore911.versioning.persistence.model.Wildcard;
import de.egore911.versioning.ui.logic.DeploymentCalculator;
import de.egore911.versioning.util.SessionUtil;
import de.egore911.versioning.util.security.RequiresPermission;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@Named
@RequiresPermission(Permission.ADMIN_SETTINGS)
public class ServerDetail extends AbstractDetail<Server> {

	private static final long serialVersionUID = 8312067615354733025L;

	private static final Logger log = LoggerFactory
			.getLogger(ServerDetail.class);

	private final DeploymentCalculator deploymentCalculator = new DeploymentCalculator();

	@Inject
	private EntityManager entityManager;
	private List<Project> origProjects;

	@Override
	protected ServerDao getDao() {
		return BeanProvider.getContextualReference(ServerDao.class);
	}

	@Override
	protected Server createEmpty() {
		origProjects = new ArrayList<>();
		return new Server();
	}

	@Override
	protected Server load() {
		Server server = super.load();
		if (server != null) {
			origProjects = new ArrayList<>(server.getConfiguredProjects());
		}
		return server;
	}

	public List<Version> getDeployedVersions() {
		if (!isManaged()) {
			return Collections.emptyList();
		}
		return deploymentCalculator.getDeployedVersions(instance);
	}

	public List<Version> getDeployableVersions() {
		if (!isManaged()) {
			return Collections.emptyList();
		}
		return deploymentCalculator.getDeployableVersions(instance);
	}

	public String addVariable() {
		Variable variable = new Variable();
		variable.setServer(getInstance());
		getInstance().getVariables().add(variable);
		return "";
	}

	public String deleteVariable(Variable variable) {
		Server server = getInstance();
		server.getVariables().remove(variable);
		markDelete(variable);
		setInstance(server);
		return "";
	}

	// Replacement
	public String chooseReplacement() {
		Server server = getInstance();
		ActionReplacement actionReplacement = new ActionReplacement();
		addWildcard(actionReplacement);
		addReplacement(actionReplacement);
		server.getActionReplacements().add(actionReplacement);
		actionReplacement.setServer(server);
		setInstance(server);
		return "";
	}

	public String deleteActionReplacement(ActionReplacement actionReplacement) {
		Server server = getInstance();
		server.getActionReplacements().remove(actionReplacement);
		markDelete(actionReplacement);
		setInstance(server);
		return "";
	}

	public String addWildcard(ActionReplacement actionReplacement) {
		Wildcard wildcard = new Wildcard();
		wildcard.setActionReplacement(actionReplacement);
		actionReplacement.getWildcards().add(wildcard);
		return "";
	}

	public String deleteWildcard(Wildcard wildcard) {
		wildcard.getActionReplacement().getWildcards().remove(wildcard);
		markDelete(wildcard);
		return "";
	}

	public String addReplacement(ActionReplacement actionReplacement) {
		Replacement replacement = new Replacement();
		replacement.setActionReplacement(actionReplacement);
		actionReplacement.getReplacements().add(replacement);
		return "";
	}

	public String deleteReplacement(Replacement replacement) {
		replacement.getActionReplacement().getReplacements()
				.remove(replacement);
		markDelete(replacement);
		return "";
	}

	public String addReplacementFile(ActionReplacement actionReplacement) {
		Replacementfile replacementFile = new Replacementfile();
		replacementFile.setActionReplacement(actionReplacement);
		actionReplacement.getReplacementFiles().add(replacementFile);
		return "";
	}

	public String deleteReplacementfile(Replacementfile replacementfile) {
		replacementfile.getActionReplacement().getReplacementFiles()
				.remove(replacementfile);
		markDelete(replacementfile);
		return "";
	}

	@Transactional
	public String save() {

		if (!validate("server")) {
			return "";
		}

		if (getInstance().getName().contains("/")) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ResourceBundle bundle = SessionUtil.getBundle();
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					bundle.getString("invalid_chars_in_name"),
					bundle.getString("invalid_chars_in_name_detail"));
			facesContext.addMessage("main:server_name", message);
			return "";
		}

		if (icon != null) {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			String filename = getFilename(icon);
			try (InputStream inputStream = icon.getInputStream()) {
				IOUtils.copy(inputStream, byteArrayOutputStream);
			} catch (IOException e) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_WARN, e.getLocalizedMessage(),
						e.getLocalizedMessage());
				facesContext.addMessage("main", message);
				log.error(e.getMessage(), e);
				return "";
			}
			BinaryData binaryData = new BinaryData();
			binaryData.setFilename(filename);
			byte[] bytes = byteArrayOutputStream.toByteArray();
			binaryData.setSize(bytes.length);
			binaryData.setData(bytes);
			binaryData.setContentType(URLConnection
					.guessContentTypeFromName(filename));
			getInstance().setIcon(binaryData);
		}

		for (Project project : getInstance().getConfiguredProjects()) {
			if (!project.getConfiguredServers().contains(getInstance())) {
				project.getConfiguredServers().add(getInstance());
			}
		}

		ProjectDao projectDao = BeanProvider
				.getContextualReference(ProjectDao.class);
		for (Project project : origProjects) {
			if (!getInstance().getConfiguredProjects().contains(project)) {
				project = projectDao.reattach(project);
				project.getConfiguredServers().remove(getInstance());
				projectDao.save(project);
			}
		}

		getDao().save(getInstance());

		origProjects = null;

		for (IntegerDbObject deletion : getDeletions()) {
			if (deletion.getId() != null) {
				deletion = entityManager.merge(deletion);
				entityManager.remove(deletion);
			}
		}

		setInstance(null);
		return "/servers.xhtml";
	}

	private Part icon;

	public Part getIcon() {
		return icon;
	}

	public void setIcon(Part icon) {
		this.icon = icon;
	}

	private static String getFilename(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			System.out.println(cd);
		}
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String filename = cd.substring(cd.indexOf('=') + 1).trim()
						.replace("\"", "");
				return filename.substring(filename.lastIndexOf('/') + 1)
						.substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
			}
		}
		return null;
	}

}
