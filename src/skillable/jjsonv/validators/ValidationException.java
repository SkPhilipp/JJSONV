package skillable.jjsonv.validators;

import java.util.ArrayList;
import java.util.List;

public class ValidationException extends Throwable {

	private static final long serialVersionUID = 1L;

	private final List<ValidationExceptionElement> trace;

	public List<ValidationExceptionElement> getTrace() {
		return this.trace;
	}

	public ValidationException(ValidationExceptionElement start) {
		super();
		this.trace = new ArrayList<ValidationExceptionElement>();
		this.trace.add(start);
	}

	public void add(ValidationExceptionElement element) {
		this.trace.add(element);
	}

}
