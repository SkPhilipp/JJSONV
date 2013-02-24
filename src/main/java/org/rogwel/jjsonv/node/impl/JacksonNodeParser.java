package org.rogwel.jjsonv.node.impl;

import java.io.File;

import org.codehaus.jackson.map.ObjectMapper;
import org.rogwel.jjsonv.node.Node;
import org.rogwel.jjsonv.node.NodeParser;
import org.rogwel.jjsonv.node.NodeParsingException;

public class JacksonNodeParser implements NodeParser {

	private final ObjectMapper mapper;

	public JacksonNodeParser() {
		this.mapper = new ObjectMapper();
	}

	@Override
	public Node read(String content) throws NodeParsingException {
		try {
			return new JacksonNode(mapper.readTree(content));
		} catch (Exception e) {
			throw new NodeParsingException("Exception while parsing string content", e);
		}
	}

	@Override
	public Node read(File file) throws NodeParsingException {
		try {
			return new JacksonNode(mapper.readTree(file));
		} catch (Exception e) {
			throw new NodeParsingException("Exception while parsing string content", e);
		}
	}

}
