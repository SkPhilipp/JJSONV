package com.skillable.jjsonv;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import com.skillable.jjsonv.validators.ArrayValidator;
import com.skillable.jjsonv.validators.ObjectValidator;
import com.skillable.jjsonv.validators.Validator;
import com.skillable.jjsonv.validators.elements.BooleanValidator;
import com.skillable.jjsonv.validators.elements.IntValidator;
import com.skillable.jjsonv.validators.elements.PlainObjectValidator;
import com.skillable.jjsonv.validators.elements.StringValidator;


/**
 * 
 * @author SkPhilipp
 *
 */
public class SchemaFactory {

	private final Map<String, Class<? extends Validator>> validators;

	public SchemaFactory() {
		validators = new HashMap<String, Class<? extends Validator>>();
		validators.put("string", StringValidator.class);
		validators.put("bool", BooleanValidator.class);
		validators.put("boolean", BooleanValidator.class);
		validators.put("int", IntValidator.class);
		validators.put("integer", IntValidator.class);
		validators.put("object", PlainObjectValidator.class);
	}

	private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
	private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

	/**
	 * Takes a string such as "My Project", and converts it to a normalized name "my-project"
	 */
	public static String normalize(String input) {
		if (input == null) {
			return null;
		} else {
			String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
			String normalized = Normalizer.normalize(nowhitespace, Form.NFD);
			String slug = NONLATIN.matcher(normalized).replaceAll("");
			return slug.toLowerCase(Locale.ENGLISH);
		}
	}

	/**
	 * Checks if a string equals it's normalized version, returns false if input is null.
	 */
	public static boolean isNormalized(String input) {
		String normalized = SchemaFactory.normalize(input);
		return input != null && input.equals(normalized);
	}

	/**
	 * Adds an ElementValidator class to the validator map.
	 * 
	 * @throws SchemaException when the given denoter is not normalized.
	 */
	public void add(String denoter, Class<? extends Validator> validator) throws SchemaException {
		if (SchemaFactory.isNormalized(denoter)) {
			validators.put(denoter, validator);
		} else {
			throw new SchemaException(String.format("\"%s\" is not a normalized string. You could normalize it with SchemaFactory.normalize", denoter));
		}
	}

	/**
	 * Converts a SchemaElement to a Validator and adds it to the parent.
	 * 
	 * @throws SchemaException when the given element is malformed.
	 */
	private void addElement(ObjectValidator parent, SchemaElement element) throws SchemaException {
		Validator validator;
		// Instantiate the validator.
		if (validators.containsKey(element.getType())) {
			try {
				validator = validators.get(element.getType()).newInstance();
			} catch (Exception e) {
				throw new SchemaException(String.format("Couldn't instantiate a validator of type \"%s\"", element.getType()));
			}
		} else {
			throw new SchemaException(String.format("Unknown validator type \"%s\"", element.getType()));
		}
		// Check if the element is an object, and add child nodes.
		if (element.isObject()) {
			if (validator instanceof ObjectValidator) {
				for (SchemaElement child : element.getChildren()) {
					this.addElement((ObjectValidator) validator, child);
				}
			} else {
				throw new SchemaException(String.format("A validator of type \"%s\" has child nodes, but is not an ObjectValidator", element.getType()));
			}
		}
		// Add array validators if the element is an array
		for (int i = 0; i < element.getArraydepth(); i++) {
			validator = new ArrayValidator(validator);
		}
		// Add the validator to the parent
		if (element.getNamespace() == null) {
			parent.set(element.getName(), validator);
		} else {
			parent.set(String.format("[%s]%s", element.getNamespace(), element.getName()), validator);
		}
	}

	/**
	 * Creates a Schema from a reader, to validate your JSON with.
	 * 
	 * @throws SchemaException on invalid schemas.
	 */
	public Schema create(SchemaElement[] elements) throws SchemaException, IOException {
		PlainObjectValidator validator = new PlainObjectValidator();
		Schema schema = new Schema(validator);
		for (SchemaElement element : elements) {
			this.addElement(validator, element);
		}
		return schema;
	}

	/**
	 * Alias of create(SchemaElement[] elements), reads the elements using a SchemaParser.
	 */
	public Schema create(Reader reader) throws SchemaException, IOException {
		SchemaParser parser = new SchemaParser();
		return this.create(parser.load(reader));
	}

	/**
	 * Alias of create(Reader), creates a FileReader on the file.
	 */
	public Schema create(File file) throws SchemaException, IOException {
		FileReader reader = new FileReader(file);
		try {
			return this.create(new FileReader(file));
		} finally {
			reader.close();
		}
	}

}
