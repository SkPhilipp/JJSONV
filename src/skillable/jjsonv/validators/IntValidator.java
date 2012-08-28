package skillable.jjsonv.validators;

import org.codehaus.jackson.JsonNode;

public class IntValidator extends ElementValidator {

	public boolean ok(JsonNode node) {
		return node.isInt();
	}

}
