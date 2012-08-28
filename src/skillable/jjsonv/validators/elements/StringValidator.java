package skillable.jjsonv.validators.elements;

import org.codehaus.jackson.JsonNode;

public class StringValidator extends ElementValidator {

	public boolean ok(JsonNode node) {
		return node.isTextual();
	}

}