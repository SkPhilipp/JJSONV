package com.skillable.jjsonv.validators.trace;

import com.skillable.jjsonv.validators.Validator;

public class ValidationTraceElement {

	private final Validator validator;
	private final ValidationParams params;

	public ValidationParams getParams() {
		return params;
	}

	public Validator getValidator() {
		return validator;
	}

	public ValidationTraceElement(Validator validator, ValidationParams params) {
		this.validator = validator;
		this.params = params;
	}

}
