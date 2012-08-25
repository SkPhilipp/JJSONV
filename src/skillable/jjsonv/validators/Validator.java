package skillable.jjsonv.validators;

import org.codehaus.jackson.JsonNode;

public abstract class Validator {

	private final int schemaLine;

	abstract public void validate(JsonNode node, String nodeName,
			Integer nodeIndex) throws ValidationException;

	public int getSchemaLine() {
		return schemaLine;
	}

	public Validator(int schemaLine) {
		this.schemaLine = schemaLine;
	}

}
