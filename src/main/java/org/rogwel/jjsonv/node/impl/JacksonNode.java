package org.rogwel.jjsonv.node.impl;

import java.util.Iterator;

import org.codehaus.jackson.JsonNode;
import org.rogwel.jjsonv.node.Node;

public class JacksonNode implements Node {

	private JsonNode inner;

	public JacksonNode(JsonNode inner){
		this.inner = inner;
	}

	@Override
	public Iterator<Node> getElements() {
		return new JacksonIteratorWrapper(inner.getElements());
	}

	@Override
	public Node get(String fieldName) {
		return new JacksonNode(inner.get(fieldName));
	}

	@Override
	public boolean has(String fieldName) {
		return inner.has(fieldName);
	}

	@Override
	public boolean isArray() {
		return inner.isArray();
	}

	@Override
	public boolean isBoolean() {
		return inner.isBoolean();
	}

	@Override
	public boolean isInt() {
		return inner.isInt();
	}

	@Override
	public boolean isTextual() {
		return inner.isTextual();
	}

	@Override
	public String getTextValue() {
		return inner.getTextValue();
	}

}
