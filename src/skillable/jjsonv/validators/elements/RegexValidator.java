package skillable.jjsonv.validators.elements;

import java.util.regex.Pattern;

import org.codehaus.jackson.JsonNode;

import skillable.jjsonv.validators.ValidationContext;

public class RegexValidator extends ElementValidator {

	@Override
	public boolean ok(JsonNode node, ValidationContext context) {
		return node.isTextual() && pattern.matcher(node.asText()).find();
	}

	private final Pattern pattern;

	public RegexValidator(String regex) {
		pattern = Pattern.compile(regex);
	}

}
