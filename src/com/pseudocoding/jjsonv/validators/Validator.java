package com.pseudocoding.jjsonv.validators;

import com.pseudocoding.jjsonv.validators.trace.ValidationException;
import com.pseudocoding.jjsonv.validators.trace.ValidationParams;

/**
 * 
 * @author SkPhilipp
 *
 */
public interface Validator {

	void validate(ValidationParams params, ValidationContext context) throws ValidationException;

}
