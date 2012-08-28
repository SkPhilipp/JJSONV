package skillable.jjsonv.validators;

import org.codehaus.jackson.JsonNode;

public class BooleanValidator extends ElementValidator {

	public boolean ok(JsonNode node) {
		return node.isBoolean();
	}

}
