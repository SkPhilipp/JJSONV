package skillable.jjsonv.validators;

import skillable.jjsonv.validators.trace.ValidationException;
import skillable.jjsonv.validators.trace.ValidationParams;

/**
 * 
 * @author SkPhilipp
 *
 */
public interface Validator {

	void validate(ValidationParams params, ValidationContext context) throws ValidationException;

}
