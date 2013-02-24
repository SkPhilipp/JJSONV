package org.rogwel.jjsonv.validators.elements;

import org.rogwel.jjsonv.node.Node;
import org.rogwel.jjsonv.validators.ObjectValidator;
import org.rogwel.jjsonv.validators.ValidationContext;

public class PlainObjectValidator extends ObjectValidator {

	@Override
	public boolean ok(Node node, ValidationContext context) {
		return true;
	}

}
