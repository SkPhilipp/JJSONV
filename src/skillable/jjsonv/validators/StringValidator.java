package skillable.jjsonv.validators;

import org.codehaus.jackson.JsonNode;

public class StringValidator extends Validator {

	@Override
	public void validate(JsonNode node) throws ValidationException {
		if (node.isTextual() == false)
			throw new ValidationException(this, node);
	}

	public StringValidator(int schemaLine) {
		super(schemaLine);
	}

}