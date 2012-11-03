package skillable.jjsonv;

import org.codehaus.jackson.JsonNode;

import skillable.jjsonv.validators.ValidationContext;
import skillable.jjsonv.validators.elements.PlainObjectValidator;
import skillable.jjsonv.validators.trace.ValidationException;
import skillable.jjsonv.validators.trace.ValidationParams;

/**
 * 
 * @author SkPhilipp
 *
 */
public class Schema {

	private final PlainObjectValidator validator;

	protected Schema(PlainObjectValidator validator) {
		this.validator = validator;
	}

	/**
	 * Validates a JsonNode by the given Schema.
	 * @return ValidationContext containing data put in by child validators.
	 * @throws ValidationException when the JsonNode does not match the schema.
	 */
	public ValidationContext validate(JsonNode node) throws ValidationException {
		ValidationParams params = new ValidationParams(node, null, false);
		ValidationContext context = new ValidationContext();
		validator.validate(params, context);
		return context;
	}

}
