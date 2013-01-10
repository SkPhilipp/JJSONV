package com.skillable.jjsonv.validators;

import com.skillable.jjsonv.validators.trace.ValidationException;
import com.skillable.jjsonv.validators.trace.ValidationParams;

/**
 * 
 * @author SkPhilipp
 *
 */
public interface Validator {

	void validate(ValidationParams params, ValidationContext context) throws ValidationException;

}
