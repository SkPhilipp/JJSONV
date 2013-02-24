package org.rogwel.jjsonv.node.impl;

import java.util.Iterator;

import org.codehaus.jackson.JsonNode;
import org.rogwel.jjsonv.node.Node;

public class JacksonIteratorWrapper implements Iterator<Node> {

	private Iterator<JsonNode> inner;

	public JacksonIteratorWrapper(Iterator<JsonNode> inner) {
		this.inner = inner;
	}

	public boolean hasNext() {
		return inner.hasNext();
	}

	public Node next() {
		return new JacksonNode(inner.next());
	}

	public void remove() {
		inner.remove();
	}

}
