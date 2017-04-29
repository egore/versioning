package de.egore911.versioning.ui.servlets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egore911.versioning.persistence.model.AbstractRemoteActionEntity;
import de.egore911.versioning.persistence.model.ActionCheckoutEntity;
import de.egore911.versioning.persistence.model.ActionCopyEntity;
import de.egore911.versioning.persistence.model.ActionExtractionEntity;
import de.egore911.versioning.persistence.model.ActionReplacementEntity;
import de.egore911.versioning.persistence.model.ExtractionEntity;
import de.egore911.versioning.persistence.model.MavenArtifactEntity;
import de.egore911.versioning.persistence.model.ProjectEntity;
import de.egore911.versioning.persistence.model.ReplacementEntity;
import de.egore911.versioning.persistence.model.ServerEntity;
import de.egore911.versioning.persistence.model.VariableEntity;
import de.egore911.versioning.persistence.model.VersionEntity;
import de.egore911.versioning.ui.logic.DeploymentCalculator;
import de.egore911.versioning.util.UrlUtil;

public abstract class Renderer {

	private static final Logger log = LoggerFactory
			.getLogger(Renderer.class);

	private final DeploymentCalculator deploymentCalculator = new DeploymentCalculator();

	private static String replaceVariables(String s, Map<String, String> replace) {
		Matcher m = VariableEntity.VARIABLE_PATTERN.matcher(s);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			if (replace.containsKey(m.group(1))) {
				m.appendReplacement(sb, replace.get(m.group(1)));
			}
		}
		m.appendTail(sb);
		return sb.toString();
	}

	protected abstract void renderHeader(StringBuilder builder);

	protected abstract void renderFooter(StringBuilder builder);

	protected abstract void renderField(StringBuilder builder, int indent,
			String name, String value);

	protected abstract void renderComment(StringBuilder builder, int indent,
			String comment);

	protected abstract void renderArrayStart(StringBuilder builder, int indent,
			String name);

	protected abstract void renderArrayEnd(StringBuilder builder, int indent,
			String name);

	protected abstract void renderArrayElementStart(StringBuilder builder,
			int indent, String name);

	protected abstract void renderArrayElementEnd(StringBuilder builder,
			int indent, String name);

	protected abstract void renderObjectStart(StringBuilder builder,
			int indent, String name);

	protected abstract void renderObjectEnd(StringBuilder builder, int indent,
			String name);

	protected abstract void renderArrayElement(StringBuilder builder,
			int indent, String name, String value);

	public final String render(ServerEntity server) {
		StringBuilder builder = new StringBuilder();
		renderHeader(builder);
		renderField(builder, 1, "name", server.getName());
		renderField(builder, 1, "targetdir", server.getTargetdir());
		renderComment(builder, 1, server.getDescription());

		Map<String, String> replace = new HashMap<>();
		for (VariableEntity variable : server.getVariables()) {
			replace.put(variable.getName(), variable.getValue());
		}

		List<VersionEntity> versions = deploymentCalculator
				.getDeployableVersions(server);
		renderArrayStart(builder, 1, "deployments");
		for (VersionEntity version : versions) {
			ProjectEntity project = version.getProject();

			List<ActionCopyEntity> actionCopies = project.getActionCopies();
			List<ActionExtractionEntity> actionExtractions = project
					.getActionExtractions();
			List<ActionCheckoutEntity> actionCheckouts = project.getActionCheckouts();
			List<ActionReplacementEntity> actionReplacements = project
					.getActionReplacements();

			if (!actionCopies.isEmpty() || !actionExtractions.isEmpty()
					|| !actionCheckouts.isEmpty()
					|| !actionReplacements.isEmpty()) {

				renderComment(builder, 2, project.getName());

				renderArrayElementStart(builder, 2, "deployment");

				appendCopyActions(builder, server, replace, version, project,
						actionCopies);

				appendExtractionActions(builder, server, replace, version,
						project, actionExtractions);

				appendCheckoutActions(builder, server, replace, version,
						project, actionCheckouts);

				appendReplacementActions(builder, server, actionReplacements);

				renderArrayElementEnd(builder, 2, "deployment");
			}
		}

		appendServerConfigurationCheckout(builder, server, replace);

		renderArrayEnd(builder, 1, "deployments");
		renderFooter(builder);

		return builder.toString();
	}

	private void appendServerConfigurationCheckout(StringBuilder builder,
			ServerEntity server, Map<String, String> replace) {
		List<ActionReplacementEntity> actionReplacements = server
				.getActionReplacements();
		if (server.getVcsHost() != null || !actionReplacements.isEmpty()) {
			renderComment(builder, 2, server.getName());
			renderObjectStart(builder, 2, "deployment");
		}

		if (server.getVcsHost() != null) {
			renderObjectStart(builder, 3, "checkout");
			renderField(builder, 4, "target",
					UrlUtil.concatenateUrlWithSlashes(server.getTargetdir(),
							replaceVariables(server.getTargetPath(), replace)));
			renderField(builder, 4, server.getVcsHost().getVcs().name(), server
					.getVcsHost().getUri() + server.getVcsPath());
			renderObjectEnd(builder, 3, "checkout");
		}

		appendReplacementActions(builder, server, actionReplacements);

		if (server.getVcsHost() != null || !actionReplacements.isEmpty()) {
			renderObjectEnd(builder, 2, "deployment");
		}
	}

	private void appendCopyActions(StringBuilder builder, ServerEntity server,
			Map<String, String> replace, VersionEntity version, ProjectEntity project,
			List<ActionCopyEntity> actionCopies) {
		for (ActionCopyEntity actionCopy : actionCopies) {
			renderObjectStart(builder, 3, "copy");
			String transformedVcsTag = version.getTransformedVcsTag();
			if (appendUrl(4, project, version, actionCopy, transformedVcsTag,
					builder)) {
				renderField(builder, 4, "target",
						UrlUtil.concatenateUrlWithSlashes(
								server.getTargetdir(),
								replaceVariables(actionCopy.getTargetPath(),
										replace)));
				if (StringUtils.isNotEmpty(actionCopy.getTargetFilename())) {
					renderField(builder, 4, "filename",
							actionCopy.getTargetFilename());
				}
			}
			renderObjectEnd(builder, 3, "copy");
		}
	}

	private void appendExtractionActions(StringBuilder builder, ServerEntity server,
			Map<String, String> replace, VersionEntity version, ProjectEntity project,
			List<ActionExtractionEntity> actionExtractions) {
		for (ActionExtractionEntity actionExtraction : actionExtractions) {
			renderObjectStart(builder, 3, "extract");
			String transformedVcsTag = version.getTransformedVcsTag();
			if (appendUrl(4, project, version, actionExtraction,
					transformedVcsTag, builder)) {
				renderArrayStart(builder, 4, "extractions");
				for (ExtractionEntity extraction : actionExtraction.getExtractions()) {
					renderArrayElementStart(builder, 5, "extraction");

					renderField(builder, 6, "source",
							replaceVariables(extraction.getSource(), replace));

					renderField(builder, 6, "destination",
							UrlUtil.concatenateUrlWithSlashes(
									server.getTargetdir(),
									replaceVariables(
											extraction.getDestination(),
											replace)));

					renderArrayElementEnd(builder, 5, "extraction");
				}
				renderArrayEnd(builder, 4, "extractions");
			}
			renderObjectEnd(builder, 3, "extract");
		}
	}

	private void appendCheckoutActions(StringBuilder builder, ServerEntity server,
			Map<String, String> replace, VersionEntity version, ProjectEntity project,
			List<ActionCheckoutEntity> actionCheckouts) {
		for (ActionCheckoutEntity actionCheckout : actionCheckouts) {
			renderObjectStart(builder, 3, "checkout");
			renderField(builder, 4, "target",
					UrlUtil.concatenateUrlWithSlashes(
							server.getTargetdir(),
							replaceVariables(actionCheckout.getTargetPath(),
									replace)));
			renderField(builder, 4, project.getVcsHost().getVcs().toString(),
					project.getVcsHost().getUri() + project.getVcsPath());
			renderField(builder, 4, "tag", version.getVcsTag());
			renderObjectEnd(builder, 3, "checkout");
		}
	}

	private void appendReplacementActions(StringBuilder builder, ServerEntity server,
			List<ActionReplacementEntity> actionReplacements) {
		for (ActionReplacementEntity actionReplacement : actionReplacements) {
			renderObjectStart(builder, 3, "replace");
			renderField(builder, 4, "basepath", server.getTargetdir());
			renderArrayStart(builder, 4, "wildcards");
			for (String wildcard : actionReplacement.getWildcards()) {
				renderArrayElement(builder, 5, "wildcard", wildcard);
			}
			renderArrayEnd(builder, 4, "wildcards");
			renderArrayStart(builder, 4, "replacements");
			for (ReplacementEntity replacement : actionReplacement.getReplacements()) {
				renderArrayElementStart(builder, 5, "replacement");
				renderField(builder, 6, "variable", replacement.getVariable());
				renderField(builder, 6, "value", replacement.getValue());
				renderArrayElementEnd(builder, 5, "replacement");
			}
			renderArrayEnd(builder, 4, "replacements");

			if (!actionReplacement.getReplacementfiles().isEmpty()) {
				renderArrayStart(builder, 4, "replacementfiles");
				for (String replacementFile : actionReplacement
						.getReplacementfiles()) {
					renderArrayElement(builder, 5, "replacementfile",
							replacementFile);
				}
				renderArrayEnd(builder, 4, "replacementfiles");
			}

			renderObjectEnd(builder, 3, "replace");
		}
	}

	private boolean appendUrl(int indent, ProjectEntity project, VersionEntity version,
			AbstractRemoteActionEntity action, String transformedVcsTag,
			StringBuilder builder) {
		if (action.getMavenArtifact() != null) {

			MavenArtifactEntity mavenArtifact = action.getMavenArtifact();
			if (mavenArtifact.getMavenRepository() == null) {
				log.error(
						"Found maven artifact {}:{}:{} without maven repository, skipping!",
						mavenArtifact.getGroupId(),
						mavenArtifact.getArtifactId(), version.getVcsTag());
				return false;
			}

			String packaging = mavenArtifact.getPackaging();
			if (StringUtils.isEmpty(packaging)) {
				packaging = "jar";
			}

			String filename = mavenArtifact.getArtifactId() + "-"
					+ transformedVcsTag + "." + packaging;
			String url = UrlUtil.concatenateUrlWithSlashes(mavenArtifact
					.getMavenRepository().getBaseUrl(), mavenArtifact
					.getGroupId().replace('.', '/'), mavenArtifact
					.getArtifactId(), transformedVcsTag, filename);

			renderField(builder, indent, "url", url);
		} else if (action.getSpacerUrl() != null) {
			renderField(builder, indent, "url", action.getSpacerUrl().getUrl()
					.replace("[VERSION]", transformedVcsTag));
		} else {
			log.error(
					"Found neither maven artifact nor spacerUrl in project {}",
					project.getName());
			return false;
		}

		return true;
	}

}
