package skillable.jjsonv.validators;

import java.util.Iterator;

import org.codehaus.jackson.JsonNode;

import skillable.jjsonv.validators.trace.ValidationException;
import skillable.jjsonv.validators.trace.ValidationParams;
import skillable.jjsonv.validators.trace.ValidationTrace;
import skillable.jjsonv.validators.trace.ValidationTraceElement;

public class ArrayValidator extends Validator {

	private final Validator validator;

	public ArrayValidator(Validator validator) {
		this.validator = validator;
	}

	@Override
	protected final void validate(ValidationTrace trace, ValidationParams params)
			throws ValidationException {
		JsonNode node = params.getNode();
		trace.add(new ValidationTraceElement(this, params));
		if (node.isArray() == false)
			throw new ValidationException(trace);
		final Iterator<JsonNode> iter = node.getElements();
		Integer index = 0;
		while (iter.hasNext()) {
			ValidationParams iterParams = new ValidationParams(iter.next(),
					index.toString(), true);
			validator.validate(trace, iterParams);
			index++;
		}
		trace.pop();
	}

}
