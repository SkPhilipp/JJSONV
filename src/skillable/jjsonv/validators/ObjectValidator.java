package skillable.jjsonv.validators;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonNode;

import skillable.jjsonv.validators.trace.ValidationException;
import skillable.jjsonv.validators.trace.ValidationParams;
import skillable.jjsonv.validators.trace.ValidationTraceElement;

public class ObjectValidator extends Validator {

	private final Map<String, Validator> map;

	public ObjectValidator() {
		map = new HashMap<String, Validator>();
	}

	@Override
	protected final void validate(ValidationParams params,
			ValidationContext context) throws ValidationException {
		try {
			JsonNode node = params.getNode();
			for (Entry<String, Validator> entry : map.entrySet()) {
				final String name = entry.getKey();
				final Validator validator = entry.getValue();
				if (node.has(name) == false) {
					throw new ValidationException();
				}
				ValidationParams memberParams = new ValidationParams(
						node.get(name), name, false);
				validator.validate(memberParams, context);
			}
		} catch (ValidationException e) {
			e.add(new ValidationTraceElement(this, params));
			throw e;
		}
	}

	public void set(String key, Validator validator) {
		this.map.put(key, validator);
	}

}
