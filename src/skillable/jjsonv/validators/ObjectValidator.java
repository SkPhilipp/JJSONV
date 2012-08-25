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
	public void validate(JsonNode node, String nodeName, Integer nodeIndex)
			throws ValidationException {
		try {
			for (Entry<String, Validator> entry : map.entrySet()) {
				final String name = entry.getKey();
				final Validator validator = entry.getValue();
				if (node.has(name) == false) {
					throw new ValidationException(
							new ValidationExceptionElement(this, node, name,
									null));
				}
				validator.validate(node.get(name), name, null);
			}
		} catch (ValidationException e) {
			e.add(new ValidationExceptionElement(this, node, nodeName,
					nodeIndex));
			throw e;
		}
	}

	public void set(String key, Validator validator) {
		this.map.put(key, validator);
	}

}
