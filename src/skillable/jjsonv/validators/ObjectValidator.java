package skillable.jjsonv.validators;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonNode;

import skillable.jjsonv.validators.trace.ValidationException;
import skillable.jjsonv.validators.trace.ValidationParams;
import skillable.jjsonv.validators.trace.ValidationTrace;
import skillable.jjsonv.validators.trace.ValidationTraceElement;

public class ObjectValidator extends Validator {

	private final Map<String, Validator> map;

	public ObjectValidator() {
		map = new HashMap<String, Validator>();
	}

	@Override
	protected final void validate(ValidationTrace trace, ValidationParams params)
			throws ValidationException {
		JsonNode node = params.getNode();
		trace.add(new ValidationTraceElement(this, params));
		try {
			for (Entry<String, Validator> entry : map.entrySet()) {
				final String name = entry.getKey();
				final Validator validator = entry.getValue();
				if (node.has(name) == false) {
					throw new ValidationException(trace);
				}
				ValidationParams memberParams = new ValidationParams(
						node.get(name), name, false);
				validator.validate(trace, memberParams);
			}
		} catch (ValidationException e) {
			throw e;
		}
		trace.pop();
	}

	public void set(String key, Validator validator) {
		this.map.put(key, validator);
	}

	public void validate(JsonNode node) throws ValidationException {
		ValidationParams params = new ValidationParams(node, null, false);
		ValidationTrace trace = new ValidationTrace();
		validate(trace, params);
	}

}
