package skillable.jjsonv.validators;

import org.codehaus.jackson.JsonNode;

public class StringValidator extends ElementValidator {

	public boolean ok(JsonNode node) {
		return node.isTextual();
	}

}