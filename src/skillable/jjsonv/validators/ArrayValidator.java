package skillable.jjsonv.validators;

import java.util.Iterator;

import org.codehaus.jackson.JsonNode;

public class ArrayValidator extends Validator {

	private final Validator elementValidator;

	@Override
	public void validate(JsonNode node) throws ValidationException {
		if (node.isArray() == false)
			throw new ValidationException(this, node);
		final Iterator<JsonNode> iter = node.getElements();
		while (iter.hasNext()) {
			elementValidator.validate(iter.next());
		}
	}

	public ArrayValidator(int schemaLine, Validator validator) {
		super(schemaLine);
		this.elementValidator = validator;
	}

}
