package org.rogwel.jjsonv.node;

import java.util.Iterator;

public interface Node {

	/**
	 * @return iterator over all child nodes
	 */
	public Iterator<Node> getElements() ;

	/**
	 * @return true if this node is an array
	 */
	public boolean isArray();

	/**
	 * @param name name of the child node
	 * @return true if this node contains a child node of given name
	 */
	public boolean has(String name);

	/**
	 * @param name name of the child node
	 * @return child node with the given name
	 */
	public Node get(String name);

	public boolean isInt();

	public boolean isBoolean();

	public boolean isTextual();

	public String getTextValue();

}
