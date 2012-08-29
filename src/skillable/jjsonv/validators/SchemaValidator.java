package skillable.jjsonv.validators;

import org.codehaus.jackson.JsonNode;

import skillable.jjsonv.validators.trace.ValidationException;
import skillable.jjsonv.validators.trace.ValidationParams;

public class SchemaValidator {

	private final ObjectValidator top;

	public ValidationContext validate(JsonNode node) throws ValidationException {
		ValidationParams params = new ValidationParams(node, null, false);
		ValidationContext context = new ValidationContext();
		top.validate(params, context);
		return context;
	}

	public SchemaValidator(ObjectValidator top) {
		this.top = top;
	}

}
