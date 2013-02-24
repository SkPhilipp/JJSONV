package org.rogwel.jjsonv.validators.trace;

import org.rogwel.jjsonv.node.Node;

public class ValidationParams {

	private final Node node;
	private final String name;
	private final Boolean array;

	public Node getNode() {
		return node;
	}

	public String getName() {
		return name;
	}

	public Boolean isArray() {
		return array;
	}

	public ValidationParams(Node node, String name, Boolean array) {
		this.node = node;
		this.name = name;
		this.array = array;
	}

}
