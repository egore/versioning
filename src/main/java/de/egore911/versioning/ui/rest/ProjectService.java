package de.egore911.versioning.ui.rest;

import java.text.MessageFormat;
import java.util.List;
import java.util.regex.Matcher;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.subject.Subject;

import de.egore911.appframework.ui.exceptions.BadArgumentException;
import de.egore911.appframework.ui.rest.AbstractResourceService;
import de.egore911.versioning.persistence.dao.ProjectDao;
import de.egore911.versioning.persistence.model.ActionCopyEntity;
import de.egore911.versioning.persistence.model.ActionExtractionEntity;
import de.egore911.versioning.persistence.model.ExtractionEntity;
import de.egore911.versioning.persistence.model.ProjectEntity;
import de.egore911.versioning.persistence.model.ServerEntity;
import de.egore911.versioning.persistence.model.VariableEntity;
import de.egore911.versioning.persistence.selector.ProjectSelector;
import de.egore911.versioning.ui.dto.Project;
import de.egore911.versioning.util.SessionUtil;
import de.egore911.versioning.util.vcs.Provider.Tag;

@Path("project")
public class ProjectService
		extends AbstractResourceService<Project, ProjectEntity> {

	@Override
	protected Class<Project> getDtoClass() {
		return Project.class;
	}

	@Override
	protected Class<ProjectEntity> getEntityClass() {
		return ProjectEntity.class;
	}

	@Override
	protected ProjectSelector getSelector(Subject subject) {
		return new ProjectSelector();
	}

	@Override
	protected ProjectDao getDao() {
		return new ProjectDao();
	}

	@Override
	protected void validate(Project dto, ProjectEntity entity) {
		super.validate(dto, entity);

		for (ActionCopyEntity actionCopy : entity.getActionCopies()) {
			checkVariableExists(entity, actionCopy.getTargetPath());
		}
		for (ActionExtractionEntity actionExtraction : entity
				.getActionExtractions()) {
			for (ExtractionEntity extraction : actionExtraction
					.getExtractions()) {
				checkVariableExists(entity, extraction.getSource());
				checkVariableExists(entity, extraction.getDestination());
			}
		}
	}

	private void checkVariableExists(ProjectEntity entity, String s) {
		Matcher m = VariableEntity.VARIABLE_PATTERN.matcher(s);
		while (m.find()) {
			String variableName = m.group(1);
			for (ServerEntity server : entity.getConfiguredServers()) {
				boolean found = false;
				for (VariableEntity variable : server.getVariables()) {
					if (variable.getName().equals(variableName)) {
						found = true;
						break;
					}
				}
				if (!found) {
					throw new BadArgumentException(MessageFormat.format(
							SessionUtil.getBundle().getString(
									"missing_variable_X_for_server_Y"),
							variableName, server.getName()));
				}
			}
		}
	}

	@Path("{id}/tags")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Tag> getTags(@PathParam("id") Integer id) {
		ProjectEntity project = getDao().findById(id);
		return project.getProvider().getTags();
	}
}
