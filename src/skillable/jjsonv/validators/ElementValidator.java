package skillable.jjsonv.validators;

import org.codehaus.jackson.JsonNode;

public abstract class ElementValidator extends Validator {

	public ElementValidator(int schemaLine) {
		super(schemaLine);
	}

	abstract public boolean ok(JsonNode json);

	@Override
	public void validate(JsonNode node, String nodeName, Integer nodeIndex)
			throws ValidationException {
		{
			if (this.ok(node) == false) {
				throw new ValidationException(new ValidationExceptionElement(
						this, node, nodeName, nodeIndex));
			}
		}
	}

}
