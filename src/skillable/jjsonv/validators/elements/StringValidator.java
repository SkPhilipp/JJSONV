package skillable.jjsonv.validators.elements;

import org.codehaus.jackson.JsonNode;

import skillable.jjsonv.validators.ElementValidator;
import skillable.jjsonv.validators.ValidationContext;

/**
 * 
 * @author SkPhilipp
 *
 */
public class StringValidator extends ElementValidator {

	@Override
	public boolean ok(JsonNode node, ValidationContext context) {
		return node.isTextual();
	}

}