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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
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
import de.egore911.versioning.util.EntityManagerUtil;
import de.egore911.versioning.util.SessionUtil;
import de.egore911.versioning.util.security.RequiresPermission;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@ManagedBean(name = "serverDetail")
@RequestScoped
@RequiresPermission(Permission.ADMIN_SETTINGS)
public class ServerDetail extends AbstractDetail<Server> {

	private static final Logger log = LoggerFactory
			.getLogger(ServerDetail.class);

	private final DeploymentCalculator deploymentCalculator = new DeploymentCalculator();

	@Override
	protected ServerDao getDao() {
		return new ServerDao();
	}

	@Override
	protected Server createEmpty() {
		HttpSession session = SessionUtil.getSession();
		session.setAttribute("server_" + null + "_origProjects",
				new ArrayList<>());
		return new Server();
	}

	@Override
	protected Server load() {
		Server server = super.load();
		if (server != null) {
			HttpSession session = SessionUtil.getSession();
			session.setAttribute("server_" + server.getId() + "_origProjects",
					new ArrayList<>(server.getConfiguredProjects()));
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

		HttpSession session = SessionUtil.getSession();
		List<Project> origProjects = (List<Project>) session
				.getAttribute("server_" + getInstance().getId()
						+ "_origProjects");
		session.setAttribute("server_" + getInstance().getId()
				+ "_origProjects", null);
		ProjectDao projectDao = new ProjectDao();

		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		try {
			for (Project project : origProjects) {
				if (!getInstance().getConfiguredProjects().contains(project)) {
					project = projectDao.reattach(project);
					project.getConfiguredServers().remove(getInstance());
					projectDao.save(project);
				}
			}

			getDao().save(getInstance());

			for (IntegerDbObject deletion : getDeletions()) {
				if (deletion.getId() != null) {
					deletion = em.merge(deletion);
					em.remove(deletion);
				}
			}

			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
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
