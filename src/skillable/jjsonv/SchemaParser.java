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
import skillable.jjsonv.validators.BooleanValidator;
import skillable.jjsonv.validators.ElementValidator;
import skillable.jjsonv.validators.IntValidator;
import skillable.jjsonv.validators.ObjectValidator;
import skillable.jjsonv.validators.RegexValidator;
import skillable.jjsonv.validators.StringValidator;
import skillable.jjsonv.validators.Validator;

public class SchemaParser {

	public final static Pattern LinePattern = Pattern
			.compile("^([\\t]*)([a-zA-Z0-9 ]*)[\\s]*:[\\s]*([\\S]+)$");

	public final Map<String, Class<? extends ElementValidator>> elementValidators;

	public SchemaParser() {
		elementValidators = new HashMap<String, Class<? extends ElementValidator>>();
		elementValidators.put("String", StringValidator.class);
		elementValidators.put("Bool", BooleanValidator.class);
		elementValidators.put("Boolean", BooleanValidator.class);
		elementValidators.put("Int", IntValidator.class);
		elementValidators.put("Integer", IntValidator.class);
	}

	public ObjectValidator load(Reader reader) throws SchemaParsingException,
			IOException {
		BufferedReader lineReader = new BufferedReader(reader);
		String lineContent = null;
		Integer lineCount = 0;

		final Stack<ObjectValidator> stack = new Stack<ObjectValidator>();
		final ObjectValidator top = new ObjectValidator();
		stack.push(top);

		while ((lineContent = lineReader.readLine()) != null) {
			lineCount++;
			// -- Match & extract data
			final Matcher matcher = LinePattern.matcher(lineContent);
			if (matcher.find() == false)
				throw new SchemaParsingException(String.format(
						"Syntax error at line %d ( \"%s\" )", lineCount,
						lineContent));
			int currentLevel = 1 + matcher.group(1).length();
			final String name = matcher.group(2);
			final String type = matcher.group(3);
			// -- Make stack correspond with tabs
			while (currentLevel < stack.size()) {
				stack.pop();
			}
			if (currentLevel > stack.size()) {
				throw new SchemaParsingException(String.format(
						"Unexpected indentation at line %d", lineCount));
			}
			// -- Make new Validator corresponding to the type
			final Validator validator;
			if ("Object".equals(type)) {
				ObjectValidator nodeValidator = new ObjectValidator();
				validator = nodeValidator;
				stack.peek().set(name, validator);
				stack.push(nodeValidator);
			} else if ("Object[]".equals(type)) {
				ObjectValidator nodeValidator = new ObjectValidator();
				validator = new ArrayValidator(nodeValidator);
				stack.peek().set(name, validator);
				stack.push(nodeValidator);
			} else if (type.startsWith("Regex:")) {
				String regex = type.substring("Regex:".length());
				validator = new RegexValidator(regex);
				stack.peek().set(name, validator);
			} else if (elementValidators.containsKey(type)) {
				try {
					validator = elementValidators.get(type).newInstance();
				} catch (Exception e) {
					throw new SchemaParsingException(String.format(
							"Couldn't instantiate a validator of type \"%s\"",
							type));
				}
				stack.peek().set(name, validator);
			} else {
				throw new SchemaParsingException(String.format(
						"Unknown validator type \"%s\" at line %d",
						lineContent, lineCount));
			}
		}
		return top;

	}

	public ObjectValidator load(File file) throws SchemaParsingException,
			IOException {
		Reader reader = new BufferedReader(new FileReader(file));
		try {
			return this.load(reader);
		} finally {
			reader.close();
		}
	}

}
