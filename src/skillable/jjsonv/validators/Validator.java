package skillable.jjsonv.validators;

import skillable.jjsonv.validators.trace.ValidationException;
import skillable.jjsonv.validators.trace.ValidationParams;
import skillable.jjsonv.validators.trace.ValidationTrace;

public abstract class Validator {

	abstract protected void validate(ValidationTrace trace,
			ValidationParams params) throws ValidationException;

}
