package com.pseudocoding.jjsonv.validators.elements;

import org.codehaus.jackson.JsonNode;

import com.pseudocoding.jjsonv.validators.ObjectValidator;
import com.pseudocoding.jjsonv.validators.ValidationContext;


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
