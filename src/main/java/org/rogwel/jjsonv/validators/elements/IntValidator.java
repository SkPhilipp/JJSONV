package org.rogwel.jjsonv.validators.elements;

import org.codehaus.jackson.JsonNode;

import org.rogwel.jjsonv.validators.ElementValidator;
import org.rogwel.jjsonv.validators.ValidationContext;


/**
 * 
 * @author SkPhilipp
 *
 */
public class IntValidator extends ElementValidator {

	@Override
	public boolean ok(JsonNode node, ValidationContext context) {
		return node.isInt();
	}

}
