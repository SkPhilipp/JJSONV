package org.rogwel.jjsonv.node;

import java.io.File;

public interface NodeParser {

	public Node read(String string) throws NodeParsingException;

	public Node read(File basicJson) throws NodeParsingException;

}
