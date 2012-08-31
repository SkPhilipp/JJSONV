package skillable.jjsonv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import skillable.jjsonv.validators.ArrayValidator;
import skillable.jjsonv.validators.ObjectValidator;
import skillable.jjsonv.validators.JSONValidator;
import skillable.jjsonv.validators.Validator;
import skillable.jjsonv.validators.elements.BooleanValidator;
import skillable.jjsonv.validators.elements.ElementValidator;
import skillable.jjsonv.validators.elements.IntValidator;
import skillable.jjsonv.validators.elements.RegexValidator;
import skillable.jjsonv.validators.elements.StringValidator;

public class SchemaParser {

	public final static Pattern LinePattern = Pattern
			.compile("^([\\t]*)([a-zA-Z0-9 ]*)[\\s]*:[\\s]*([\\S]+)$");

	public final Map<String, Class<? extends ElementValidator>> elementValidators;

	public SchemaParser() {
		elementValidators = new HashMap<String, Class<? extends ElementValidator>>();
		this.add("String", StringValidator.class);
		this.add("Bool", BooleanValidator.class);
		this.add("Boolean", BooleanValidator.class);
		this.add("Int", IntValidator.class);
		this.add("Integer", IntValidator.class);
	}

	/**
	 * Adds a custom ElementValidator to this SchemaParser
	 * 
	 * @param denoter
	 * @param validator
	 */
	public void add(String denoter, Class<? extends ElementValidator> validator) {
		this.elementValidators.put(denoter, validator);
	}

	/**
	 * Creates an ObjectValidator from a reader, to validate JSON with
	 * 
	 * @throws SchemaParsingException
	 *             when data is read from the reader that can't be parsed.
	 */
	// TODO: Support for ElementValidators with parameters (RegexValidator)
	// TODO: Support for arrays of non Object[] type
	public JSONValidator load(Reader reader) throws SchemaParsingException,
			IOException {

		BufferedReader lineReader = new BufferedReader(reader);
		String lineContent = null;
		Integer lineCount = 0;

		final Stack<ObjectValidator> stack = new Stack<ObjectValidator>();
		final ObjectValidator top = new ObjectValidator();
		stack.push(top);

		while ((lineContent = lineReader.readLine()) != null) {
			lineCount++;
			// Match & extract data
			final Matcher matcher = LinePattern.matcher(lineContent);
			if (matcher.find() == false)
				throw new SchemaParsingException(String.format(
						"Syntax error at line %d ( \"%s\" )", lineCount,
						lineContent));
			int currentLevel = 1 + matcher.group(1).length();
			final String name = matcher.group(2);
			final String type = matcher.group(3);
			// Make stack correspond with tabs in schema file
			while (currentLevel < stack.size()) {
				stack.pop();
			}
			if (currentLevel > stack.size()) {
				throw new SchemaParsingException(String.format(
						"Unexpected indentation at line %d", lineCount));
			}
			// Make new Validator corresponding to the type
			if ("Object".equals(type)) {
				ObjectValidator nodeValidator = new ObjectValidator();
				Validator validator = nodeValidator;
				stack.peek().set(name, validator);
				stack.push(nodeValidator);
			} else if ("Object[]".equals(type)) {
				ObjectValidator nodeValidator = new ObjectValidator();
				Validator validator = new ArrayValidator(nodeValidator);
				stack.peek().set(name, validator);
				stack.push(nodeValidator);
			} else if (type.startsWith("Regex:")) {
				String regex = type.substring("Regex:".length());
				Validator validator = new RegexValidator(regex);
				stack.peek().set(name, validator);
			} else if (elementValidators.containsKey(type)) {
				try {
					Validator validator = elementValidators.get(type)
							.newInstance();
					stack.peek().set(name, validator);
				} catch (Exception e) {
					throw new SchemaParsingException(String.format(
							"Couldn't instantiate a validator of type \"%s\"",
							type));
				}
			} else {
				throw new SchemaParsingException(String.format(
						"Unknown validator type \"%s\" at line %d",
						lineContent, lineCount));
			}
		}
		return new JSONValidator(top);

	}

	/**
	 * SchemaParser.load(Reader) alternative. This creates a FileReader around
	 * the given File, and closes it when done
	 */
	public JSONValidator load(File file) throws SchemaParsingException,
			IOException {
		Reader reader = (new FileReader(file));
		try {
			return this.load(reader);
		} finally {
			reader.close();
		}
	}

}
