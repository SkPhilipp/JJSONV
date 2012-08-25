package skillable.jjsonv.validators;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonNode;

public class ObjectValidator extends Validator {

	/**
	 * Map of each node name, with corresponding validator.
	 */
	private final Map<String, Validator> map;

	public ObjectValidator(int schemaLine) {
		super(schemaLine);
		map = new HashMap<String, Validator>();
	}

	@Override
	public void validate(JsonNode node) throws ValidationException {
		for (Entry<String, Validator> entry : map.entrySet()) {
			final String name = entry.getKey();
			final Validator validator = entry.getValue();
			if (node.has(name) == false) {
				throw new ValidationException(this, node);
			}
			validator.validate(node.get(name));
		}
	}

	public void set(String key, Validator validator) {
		this.map.put(key, validator);
	}

}
