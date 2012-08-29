package skillable.jjsonv.validators.elements;

import org.codehaus.jackson.JsonNode;

import skillable.jjsonv.validators.ValidationContext;

public class IntValidator extends ElementValidator {

	@Override
	public boolean ok(JsonNode node, ValidationContext context) {
		return node.isInt();
	}

}
