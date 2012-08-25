package skillable.jjsonv.validators;

import java.util.Iterator;

import org.codehaus.jackson.JsonNode;

public class ArrayValidator extends Validator {

	private final ElementValidator elementValidator;

	public ArrayValidator(int schemaLine, ElementValidator validator) {
		super(schemaLine);
		this.elementValidator = validator;
	}

	@Override
	public void validate(JsonNode node, String nodeName, Integer nodeIndex)
			throws ValidationException {
		if (node.isArray() == false)
			throw new ValidationException(new ValidationExceptionElement(this,
					node, nodeName, nodeIndex));
		final Iterator<JsonNode> iter = node.getElements();
		int index = 0;
		while (iter.hasNext()) {
			elementValidator.validate(iter.next(), null, index);
			index++;
		}
	}

}
