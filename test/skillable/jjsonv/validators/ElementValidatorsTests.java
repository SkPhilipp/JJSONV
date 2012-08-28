package skillable.jjsonv.validators;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import skillable.jjsonv.SchemaParser;
import skillable.jjsonv.validators.trace.ValidationException;

@SuppressWarnings("all")
public class ElementValidatorsTests {

	@Test
	public void testValidation() throws Exception {
		File basicSchema = new File("test/files/basic.jsons");
		File basicJson = new File("test/files/basic.json");
		ObjectValidator validator = SchemaParser.load(basicSchema);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(basicJson);
		validator.validate(node);
	}

	@Test
	public void testValidationTrace() throws Exception {
		try {
			File basicSchema = new File("test/files/basic.jsons");
			File basicJson = new File("test/files/basic-error.json");
			ObjectValidator validator = SchemaParser.load(basicSchema);
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(basicJson);
			validator.validate(node);
		} catch (ValidationException e) {
			assertEquals("model.members[1].size", e.getTrace().toString());
		}
	}

}
