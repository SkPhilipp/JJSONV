package org.rogwel.jjsonv;

import java.util.HashMap;
import java.util.Map;

import org.rogwel.jjsonv.node.Node;
import org.rogwel.jjsonv.validators.ElementValidator;
import org.rogwel.jjsonv.validators.ValidationContext;

public class TestValidator extends ElementValidator {

	private final Map<String, String> existingMembers;

	public TestValidator() {
		existingMembers = new HashMap<String, String>();
		existingMembers.put("Member1", "Data1");
		existingMembers.put("Member2", "Data2");
	}

	@Override
	public boolean ok(Node node, ValidationContext context) {
		// Check for valid data
		if (node.isTextual() == false)
			return false;
		// Load up our existing member
		String key = node.getTextValue();
		String value = existingMembers.get(key);
		if (value == null)
			return false;
		// Get or create our map
		@SuppressWarnings("unchecked")
		Map<String, String> members = (Map<String, String>) context.get("Members");
		if (members == null)
			members = new HashMap<String, String>();
		// Add our member which we know exists
		members.put(key, value);
		// Store our members again
		context.put("Members", members);
		return true;
	}

}
