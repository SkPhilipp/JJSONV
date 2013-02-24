package org.rogwel.jjsonv.validators.elements;

import org.rogwel.jjsonv.node.Node;
import org.rogwel.jjsonv.validators.ElementValidator;
import org.rogwel.jjsonv.validators.ValidationContext;

public class BooleanValidator extends ElementValidator {

	@Override
	public boolean ok(Node node, ValidationContext context) {
		return node.isBoolean();
	}

}
