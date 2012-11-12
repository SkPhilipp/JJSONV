package com.pseudocoding.jjsonv.validators.elements;

import org.codehaus.jackson.JsonNode;

import com.pseudocoding.jjsonv.validators.ElementValidator;
import com.pseudocoding.jjsonv.validators.ValidationContext;


/**
 * 
 * @author SkPhilipp
 *
 */
public class BooleanValidator extends ElementValidator {

	@Override
	public boolean ok(JsonNode node, ValidationContext context) {
		return node.isBoolean();
	}

}
