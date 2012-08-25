package skillable.jjsonv.validators;

import org.codehaus.jackson.JsonNode;

public class ValidationException extends Exception {

	// -- Exception Message -------------------------------

	static String buildMessage(Validator validator, JsonNode node) {
		final Integer line = validator.getSchemaLine();
		final String text = node.asText().substring(0, Math.min(50, node.asText().length()));
		return String.format("Validation error at schema line %d, node value: \"%s\"", line, text);
	}

	// -- Getters, Constructor, Etc. ----------------------

	private static final long serialVersionUID = 1L;

	private final JsonNode node;
	private final Validator validator;

	public JsonNode getNode() {
		return node;
	}

	public Validator getValidator() {
		return validator;
	}

	public ValidationException(Validator validator, JsonNode node) {
		super(ValidationException.buildMessage(validator, node));
		this.validator = validator;
		this.node = node;
	}

}
