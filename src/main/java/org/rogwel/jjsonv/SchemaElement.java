package org.rogwel.jjsonv;

import java.util.ArrayList;
import java.util.List;

public class SchemaElement {

	private final String namespace;
	private final String name;
	private final String type;
	private final int arraydepth;
	private final List<SchemaElement> children;
	private final SchemaElement parent;

	public SchemaElement(String namespace, String name, String type, int arraydepth, SchemaElement parent) {
		this.namespace = namespace;
		this.name = name;
		this.type = type;
		this.parent = parent;
		this.arraydepth = arraydepth;
		this.children = new ArrayList<SchemaElement>();
	}

	public SchemaElement() {
		this.namespace = null;
		this.name = null;
		this.type = null;
		this.parent = null;
		this.arraydepth = -1;
		this.children = new ArrayList<SchemaElement>();
	}

	public String getNamespace() {
		return namespace;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public List<SchemaElement> getChildren() {
		return children;
	}

	public void addChild(SchemaElement child) {
		this.children.add(child);
	}

	public SchemaElement getParent() {
		return parent;
	}

	public int getArraydepth() {
		return arraydepth;
	}

	public boolean isArray() {
		return arraydepth > 0;
	}

	public boolean isObject() {
		return children.size() > 0;
	}

}
