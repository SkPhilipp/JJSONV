package org.rogwel.jjsonv.validators;

import java.util.Iterator;

import org.rogwel.jjsonv.node.Node;
import org.rogwel.jjsonv.validators.trace.ValidationException;
import org.rogwel.jjsonv.validators.trace.ValidationParams;
import org.rogwel.jjsonv.validators.trace.ValidationTraceElement;

public class ArrayValidator implements Validator {

	private final Validator validator;

	public ArrayValidator(Validator validator) {
		this.validator = validator;
	}

	@Override
	public final void validate(ValidationParams params, ValidationContext context) throws ValidationException {
		Node node = params.getNode();
		try {
			if (!node.isArray()) {
				throw new ValidationException();
			}
			final Iterator<Node> iter = node.getElements();
			Integer index = 0;
			while (iter.hasNext()) {
				ValidationParams iterParams = new ValidationParams(iter.next(), index.toString(), true);
				validator.validate(iterParams, context);
				index++;
			}
		} catch (ValidationException e) {
			e.add(new ValidationTraceElement(this, params));
			throw e;
		}
	}

}
