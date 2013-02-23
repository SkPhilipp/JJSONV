package org.rogwel.jjsonv.validators;

import org.codehaus.jackson.JsonNode;

import org.rogwel.jjsonv.validators.trace.ValidationException;
import org.rogwel.jjsonv.validators.trace.ValidationParams;
import org.rogwel.jjsonv.validators.trace.ValidationTraceElement;


/**
 * 
 * @author SkPhilipp
 *
 */
public abstract class ElementValidator implements Validator {

	abstract public boolean ok(JsonNode node, ValidationContext context);

	@Override
	public final void validate(ValidationParams params, ValidationContext context) throws ValidationException {
		if (!this.ok(params.getNode(), context)) {
			ValidationException exception = new ValidationException();
			exception.add(new ValidationTraceElement(this, params));
			throw exception;
		}
	}

}
