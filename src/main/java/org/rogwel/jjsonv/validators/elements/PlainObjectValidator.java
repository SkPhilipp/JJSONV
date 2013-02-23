package org.rogwel.jjsonv.validators.elements;

import org.codehaus.jackson.JsonNode;

import org.rogwel.jjsonv.validators.ObjectValidator;
import org.rogwel.jjsonv.validators.ValidationContext;


/**
 * 
 * @author SkPhilipp
 *
 */
public class PlainObjectValidator extends ObjectValidator {

	@Override
	public boolean ok(JsonNode node, ValidationContext context) {
		return true;
	}

}
