package skillable.jjsonv.validators;

import org.codehaus.jackson.JsonNode;

public class ValidationExceptionElement {

	private final Validator validator;
	private final JsonNode node;
	private final String nodeName;
	private final Integer nodeIndex;

	public Validator getValidator() {
		return validator;
	}

	public JsonNode getNode() {
		return node;
	}

	public String getNodeName() {
		return nodeName;
	}

	public Integer getNodeIndex() {
		return nodeIndex;
	}

	public ValidationExceptionElement(Validator validator, JsonNode node,
			String nodeName, Integer nodeIndex) {
		this.validator = validator;
		this.node = node;
		this.nodeName = nodeName;
		this.nodeIndex = nodeIndex;
	}

}
