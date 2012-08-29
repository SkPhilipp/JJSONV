package skillable.jjsonv.validators;

import skillable.jjsonv.validators.trace.ValidationException;
import skillable.jjsonv.validators.trace.ValidationParams;

public abstract class Validator {

	abstract protected void validate(ValidationParams params,
			ValidationContext context) throws ValidationException;

}
