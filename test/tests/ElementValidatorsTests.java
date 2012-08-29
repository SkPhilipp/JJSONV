package tests;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import skillable.jjsonv.SchemaParser;
import skillable.jjsonv.validators.ObjectValidator;
import skillable.jjsonv.validators.trace.ValidationException;

@SuppressWarnings("all")
public class ElementValidatorsTests {

	File basicSchema = new File("test/files/basic.jsons");
	File basicJson = new File("test/files/basic.json");
	File basicJsonError = new File("test/files/basic-error.json");
	ObjectMapper mapper = new ObjectMapper();

	@Test
	public void testValidation() throws Exception {
		SchemaParser parser = new SchemaParser();
		ObjectValidator validator = parser.load(basicSchema);
		validator.validate(mapper.readTree(basicJson));
	}

	@Test
	public void testValidationTrace() throws Exception {
		try {
			SchemaParser parser = new SchemaParser();
			ObjectValidator validator = parser.load(basicSchema);
			validator.validate(mapper.readTree(basicJsonError));
		} catch (ValidationException e) {
			assertEquals("model.members[1].size", e.toString());
		}
	}

}
