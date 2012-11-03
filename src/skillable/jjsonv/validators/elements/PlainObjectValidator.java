package skillable.jjsonv.validators.elements;

import org.codehaus.jackson.JsonNode;

import skillable.jjsonv.validators.ObjectValidator;
import skillable.jjsonv.validators.ValidationContext;

/**
 * 
 * @author SkPhilipp
 *
 */
public class PlainObjectValidator extends ObjectValidator {

	@Override
	public boolean ok(JsonNode node, ValidationContext context) {
		return true;
	}

}
