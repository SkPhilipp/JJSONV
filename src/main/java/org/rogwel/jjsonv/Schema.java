package org.rogwel.jjsonv;

import org.rogwel.jjsonv.node.Node;
import org.rogwel.jjsonv.validators.ValidationContext;
import org.rogwel.jjsonv.validators.elements.PlainObjectValidator;
import org.rogwel.jjsonv.validators.trace.ValidationException;
import org.rogwel.jjsonv.validators.trace.ValidationParams;

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
	public ValidationContext validate(Node node) throws ValidationException {
		ValidationParams params = new ValidationParams(node, null, false);
		ValidationContext context = new ValidationContext();
		validator.validate(params, context);
		return context;
	}

}
