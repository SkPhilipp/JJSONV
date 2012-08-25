package skillable.jjsonv.validators;

import java.util.regex.Pattern;

import org.codehaus.jackson.JsonNode;

public class RegexValidator extends Validator {

	private final Pattern pattern;

	@Override
	public void validate(JsonNode node) throws ValidationException {
		if (node.isTextual() == false || pattern.matcher(node.asText()).find() == false)
			throw new ValidationException(this, node);
	}

	public RegexValidator(int schemaLine, String regex) {
		super(schemaLine);
		pattern = Pattern.compile(regex);
	}

}
