package skillable.jjsonv.validators.elements;

import org.codehaus.jackson.JsonNode;

import skillable.jjsonv.validators.ValidationContext;
import skillable.jjsonv.validators.Validator;
import skillable.jjsonv.validators.trace.ValidationException;
import skillable.jjsonv.validators.trace.ValidationParams;
import skillable.jjsonv.validators.trace.ValidationTraceElement;

public abstract class ElementValidator extends Validator {

	abstract public boolean ok(JsonNode node, ValidationContext context);

	@Override
	public final void validate(ValidationParams params,
			ValidationContext context) throws ValidationException {
		if (this.ok(params.getNode(), context) == false) {
			ValidationException exception = new ValidationException();
			exception.add(new ValidationTraceElement(this, params));
			throw exception;
		}
	}

}
