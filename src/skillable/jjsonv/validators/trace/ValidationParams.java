package skillable.jjsonv.validators.trace;

import org.codehaus.jackson.JsonNode;

public class ValidationParams {

	private final JsonNode node;
	private final String name;
	private final Boolean array;

	public JsonNode getNode() {
		return node;
	}

	public String getName() {
		return name;
	}

	public Boolean isArray() {
		return array;
	}

	public ValidationParams(JsonNode node, String name, Boolean array) {
		this.node = node;
		this.name = name;
		this.array = array;
	}

}
