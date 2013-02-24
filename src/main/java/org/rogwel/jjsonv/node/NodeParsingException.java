package org.rogwel.jjsonv.node;

public class NodeParsingException extends Exception {

	private static final long serialVersionUID = 1L;

	public NodeParsingException() {
		super();
	}

	public NodeParsingException(String message, Throwable cause) {
		super(message, cause);
	}

	public NodeParsingException(String message) {
		super(message);
	}

	public NodeParsingException(Throwable cause) {
		super(cause);
	}
 
}
