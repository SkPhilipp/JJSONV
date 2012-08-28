package skillable.jjsonv.validators;

import org.codehaus.jackson.JsonNode;

import skillable.jjsonv.validators.trace.ValidationException;
import skillable.jjsonv.validators.trace.ValidationParams;
import skillable.jjsonv.validators.trace.ValidationTrace;
import skillable.jjsonv.validators.trace.ValidationTraceElement;

public abstract class ElementValidator extends Validator {

	abstract public boolean ok(JsonNode json);

	@Override
	public void validate(ValidationTrace trace, ValidationParams params)
			throws ValidationException {
		trace.add(new ValidationTraceElement(this, params));
		if (this.ok(params.getNode()) == false) {
			throw new ValidationException(trace);
		}
		trace.pop();
	}

}
