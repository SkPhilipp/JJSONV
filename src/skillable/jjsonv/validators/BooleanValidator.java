package skillable.jjsonv.validators;

import org.codehaus.jackson.JsonNode;

public class BooleanValidator extends Validator {

	@Override
	public void validate(JsonNode node) throws ValidationException {
		if (node.isBoolean() == false)
			throw new ValidationException(this, node);
	}

	public BooleanValidator(int schemaLine) {
		super(schemaLine);
	}

}
