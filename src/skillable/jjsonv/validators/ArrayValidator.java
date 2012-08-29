package skillable.jjsonv.validators;

import java.util.Iterator;

import org.codehaus.jackson.JsonNode;

import skillable.jjsonv.validators.trace.ValidationException;
import skillable.jjsonv.validators.trace.ValidationParams;
import skillable.jjsonv.validators.trace.ValidationTraceElement;

public class ArrayValidator extends Validator {

	private final Validator validator;

	public ArrayValidator(Validator validator) {
		this.validator = validator;
	}

	@Override
	protected final void validate(ValidationParams params,
			ValidationContext context) throws ValidationException {
		JsonNode node = params.getNode();
		try {
			if (node.isArray() == false)
				throw new ValidationException();
			final Iterator<JsonNode> iter = node.getElements();
			Integer index = 0;
			while (iter.hasNext()) {
				ValidationParams iterParams = new ValidationParams(iter.next(),
						index.toString(), true);
				validator.validate(iterParams, context);
				index++;
			}
		} catch (ValidationException e) {
			e.add(new ValidationTraceElement(this, params));
			throw e;
		}
	}

}
