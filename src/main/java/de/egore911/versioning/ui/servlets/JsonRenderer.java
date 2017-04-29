package de.egore911.versioning.ui.servlets;

public class JsonRenderer extends Renderer {

	private boolean nextRequiresComma = false;
	private boolean nextRequiresNewline = false;

	@Override
	protected void renderHeader(StringBuilder builder) {
		builder.append("{\n");
	}

	@Override
	protected void renderFooter(StringBuilder builder) {
		builder.append("\n}\n");
	}

	@Override
	protected void renderField(StringBuilder builder, int indent, String name,
			String value) {
		if (nextRequiresComma) {
			builder.append(",");
		}
		if (nextRequiresNewline) {
			builder.append("\n");
		}
		for (int i = 0; i < indent; i++) {
			builder.append("	");
		}
		builder.append("\"").append(name).append("\": \"").append(value)
				.append("\"");
		nextRequiresComma = true;
		nextRequiresNewline = true;
	}

	@Override
	protected void renderComment(StringBuilder builder, int indent,
			String comment) {
		// Nothing
	}

	@Override
	protected void renderArrayStart(StringBuilder builder, int indent,
			String name) {
		if (nextRequiresComma) {
			builder.append(",");
		}
		if (nextRequiresNewline) {
			builder.append("\n");
		}
		for (int i = 0; i < indent; i++) {
			builder.append("	");
		}
		builder.append("\"").append(name).append("\": [");
		nextRequiresComma = false;
		nextRequiresNewline = true;
	}

	@Override
	protected void renderArrayEnd(StringBuilder builder, int indent, String name) {
		if (nextRequiresNewline) {
			builder.append('\n');
			for (int i = 0; i < indent; i++) {
				builder.append("	");
			}
		}
		builder.append("]");
		nextRequiresComma = true;
		nextRequiresNewline = true;
	}

	@Override
	protected void renderArrayElementStart(StringBuilder builder, int indent,
			String name) {
		if (nextRequiresComma) {
			builder.append(",");
		}
		if (nextRequiresNewline) {
			builder.append("\n");
		}
		for (int i = 0; i < indent; i++) {
			builder.append("	");
		}
		builder.append("{");
		nextRequiresComma = false;
		nextRequiresNewline = true;
	}

	@Override
	protected void renderArrayElementEnd(StringBuilder builder, int indent, String name) {
		if (nextRequiresNewline) {
			builder.append('\n');
			for (int i = 0; i < indent; i++) {
				builder.append("	");
			}
		}
		builder.append("}");
		nextRequiresComma = true;
		nextRequiresNewline = true;
	}

	@Override
	protected void renderArrayElement(StringBuilder builder, int indent,
			String name, String value) {
		if (nextRequiresNewline) {
			builder.append('\n');
			for (int i = 0; i < indent; i++) {
				builder.append("	");
			}
		}
		builder.append("\"").append(value).append("\"");
		nextRequiresComma = true;
		nextRequiresNewline = true;
	}

	@Override
	protected void renderObjectStart(StringBuilder builder, int indent,
			String name) {
		if (nextRequiresComma) {
			builder.append(",");
		}
		if (nextRequiresNewline) {
			builder.append("\n");
		}
		for (int i = 0; i < indent; i++) {
			builder.append("	");
		}
		builder.append("\"").append(name).append("\": {");
		nextRequiresComma = false;
		nextRequiresNewline = true;
	}

	@Override
	protected void renderObjectEnd(StringBuilder builder, int indent,
			String name) {
		if (nextRequiresNewline) {
			builder.append('\n');
			for (int i = 0; i < indent; i++) {
				builder.append("	");
			}
		}
		builder.append("}");
		nextRequiresComma = true;
		nextRequiresNewline = true;
	}
}
