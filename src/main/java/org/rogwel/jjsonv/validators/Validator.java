package org.rogwel.jjsonv.validators;

import org.rogwel.jjsonv.validators.trace.ValidationException;
import org.rogwel.jjsonv.validators.trace.ValidationParams;

public interface Validator {

	void validate(ValidationParams params, ValidationContext context) throws ValidationException;

}
