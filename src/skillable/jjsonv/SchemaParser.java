package skillable.jjsonv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import skillable.jjsonv.validators.ArrayValidator;
import skillable.jjsonv.validators.BooleanValidator;
import skillable.jjsonv.validators.IntValidator;
import skillable.jjsonv.validators.ObjectValidator;
import skillable.jjsonv.validators.RegexValidator;
import skillable.jjsonv.validators.StringValidator;
import skillable.jjsonv.validators.Validator;

//TODO: Schema Parsing exception classes
public class SchemaParser {

	public final static Pattern LinePattern = Pattern
			.compile("^([\\t]*)([a-zA-Z0-9 ]*)[\\s]*:[\\s]*([\\S]+)$");

	public static ObjectValidator load(File file) throws Exception {

		final BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		Integer lineCount = 0;

		final Stack<ObjectValidator> stack = new Stack<ObjectValidator>();
		final ObjectValidator top = new ObjectValidator();
		stack.push(top);

		try {
			while ((line = reader.readLine()) != null) {
				lineCount++;
				// -- Math & extract data
				final Matcher matcher = LinePattern.matcher(line);
				if (matcher.find() == false)
					throw new Exception(String.format(
							"Syntax error at line %d:\n\t%s", lineCount, line));
				int currentLevel = 1 + matcher.group(1).length();
				final String name = matcher.group(2);
				final String type = matcher.group(3);
				// -- Make stack correspond with tabs
				while (currentLevel < stack.size()) {
					stack.pop();
				}
				if (currentLevel > stack.size()) {
					throw new Exception("Unexpected indentation at line: "
							+ lineCount);
				}
				// -- Make new Validator corresponding to the type
				final Validator validator;
				if ("String".equals(type)) {
					validator = new StringValidator();
					stack.peek().set(name, validator);
				} else if ("Boolean".equals(type)) {
					validator = new BooleanValidator();
					stack.peek().set(name, validator);
				} else if ("Int".equals(type)) {
					validator = new IntValidator();
					stack.peek().set(name, validator);
				} else if ("Object".equals(type)) {
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
				} else {
					throw new Exception("Unknown validator type: " + type);
				}
			}
		} finally {
			reader.close();
		}
		return top;

	}

}
