package skillable.jjsonv.validators.elements;

import org.codehaus.jackson.JsonNode;

public class BooleanValidator extends ElementValidator {

	@Override
	public boolean ok(JsonNode node) {
		return node.isBoolean();
	}

}
