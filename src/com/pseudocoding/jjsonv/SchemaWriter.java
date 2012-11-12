package com.pseudocoding.jjsonv;

import java.io.IOException;
import java.io.Writer;


/**
 * 
 * @author SkPhilipp
 *
 */
public class SchemaWriter {

	/**
	 * Writes one element to writer, calls self to write children.
	 */
	private void write(SchemaElement element, Writer writer, int depth) throws IOException {
		for (int i = 0; i < depth; i++) {
			writer.write(Constants.CHAR_DEPTH);
		}
		if (element.getNamespace() != null) {
			writer.write(Constants.CHAR_NAMESPACE_BEGIN);
			writer.write(element.getNamespace());
			writer.write(Constants.CHAR_NAMESPACE_END);
		}
		writer.write(element.getName());
		writer.write(Constants.CHAR_TYPE);
		writer.write(element.getType());
		if (element.isArray()) {
			for (int i = 0; i < element.getArraydepth(); i++) {
				writer.write(Constants.STRING_ARRAY);
			}
		}
		writer.write(Constants.STRING_NEWLINE);
		for (SchemaElement child : element.getChildren()) {
			this.write(child, writer, depth + 1);
		}
	}

	/**
	 * Writes elements to writer in schema format.
	 */
	public void write(SchemaElement[] elements, Writer writer) throws IOException {
		for (SchemaElement element : elements) {
			this.write(element, writer, 0);
		}
		writer.close();
	}

}
