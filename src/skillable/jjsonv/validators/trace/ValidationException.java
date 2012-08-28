package skillable.jjsonv.validators.trace;

public class ValidationException extends Exception {

	private static final long serialVersionUID = 1L;

	private final ValidationTrace trace;

	public ValidationTrace getTrace() {
		return this.trace;
	}

	public ValidationException(ValidationTrace trace) {
		this.trace = trace;
	}

}
