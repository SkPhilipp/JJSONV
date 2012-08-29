package skillable.jjsonv.validators.elements;

import org.codehaus.jackson.JsonNode;

import skillable.jjsonv.validators.ValidationContext;

public class BooleanValidator extends ElementValidator {

	@Override
	public boolean ok(JsonNode node, ValidationContext context) {
		return node.isBoolean();
	}

}
