package skillable.jjsonv.validators;

import org.codehaus.jackson.JsonNode;

public class IntValidator extends Validator {

	@Override
	public void validate(JsonNode node) throws ValidationException {
		if (node.isInt() == false)
			throw new ValidationException(this, node);
	}

	public IntValidator(int schemaLine) {
		super(schemaLine);
	}

}
