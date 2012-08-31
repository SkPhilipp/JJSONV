package tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import skillable.jjsonv.SchemaParser;
import skillable.jjsonv.validators.JSONValidator;
import skillable.jjsonv.validators.ValidationContext;
import skillable.jjsonv.validators.trace.ValidationException;

public class Tests {

	File basicSchema = new File("test/files/basic.jsons");
	File basicCustomSchema = new File("test/files/basic-custom.jsons");
	File basicJson = new File("test/files/basic.json");
	File basicJsonError = new File("test/files/basic-error.json");
	ObjectMapper mapper = new ObjectMapper();

	@Test
	public void testValidation() throws Exception {
		SchemaParser parser = new SchemaParser();
		JSONValidator validator = parser.load(basicSchema);
		validator.validate(mapper.readTree(basicJson));
	}

	@Test
	public void testValidationTrace() throws Exception {
		try {
			SchemaParser parser = new SchemaParser();
			JSONValidator validator = parser.load(basicSchema);
			validator.validate(mapper.readTree(basicJsonError));
		} catch (ValidationException e) {
			assertEquals("model.members[1].size", e.toString());
		}
	}

	@Test
	public void textValidationContext() throws Exception {
		SchemaParser parser = new SchemaParser();
		parser.add("Test", TestValidator.class);
		JSONValidator validator = parser.load(basicCustomSchema);
		ValidationContext context = validator.validate(mapper
				.readTree(basicJson));
		@SuppressWarnings("unchecked")
		Map<String, String> members = (Map<String, String>) context
				.get("Members");
		assertEquals("Data1", members.get("Member1"));
		assertEquals("Data2", members.get("Member2"));
	}

}
