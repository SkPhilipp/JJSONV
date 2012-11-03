package tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import skillable.jjsonv.Schema;
import skillable.jjsonv.SchemaFactory;
import skillable.jjsonv.validators.ValidationContext;
import skillable.jjsonv.validators.trace.ValidationException;

/**
 * 
 * @author SkPhilipp
 *
 */
public class Tests {

	private final File basicSchema = new File("test/files/basic.jsons");
	private final File basicCustomSchema = new File("test/files/basic-custom.jsons");
	private final File basicJson = new File("test/files/basic.json");
	private final File basicJsonError = new File("test/files/basic-error.json");

	private final ObjectMapper mapper = new ObjectMapper();
	private final SchemaFactory factory = new SchemaFactory();

	/**
	 * Adds our own custom made validator
	 */
	@Before
	public void addValidator() throws Exception {
		factory.add("test", TestValidator.class);
	}

	/**
	 * Tests a validation that should succeed.
	 */
	@Test
	public void testValidation() throws Exception {
		Schema schema = factory.create(basicSchema);
		schema.validate(mapper.readTree(basicJson));
	}

	/**
	 * Tests a validation that should fail, and then tests if the failure trace is correct.
	 */
	@Test
	public void testValidationTrace() throws Exception {
		try {
			Schema schema = factory.create(basicSchema);
			schema.validate(mapper.readTree(basicJsonError));
		} catch (ValidationException e) {
			assertEquals("model.members[1].size", e.toString());
		}
	}

	/**
	 * Tests if our custom test Validator was registered, loaded, and if it correctly added data to the resulting ValidationContext
	 */
	@Test
	public void testValidationContext() throws Exception {
		// Load custom schema
		Schema schema = factory.create(basicCustomSchema);
		// Get the result of validation
		ValidationContext context = schema.validate(mapper.readTree(basicJson));
		@SuppressWarnings("unchecked")
		Map<String, String> members = (Map<String, String>) context.get("Members");
		assertEquals("Data1", members.get("Member1"));
		assertEquals("Data2", members.get("Member2"));

	}

}
