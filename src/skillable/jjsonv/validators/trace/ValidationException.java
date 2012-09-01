package skillable.jjsonv.validators.trace;

import java.util.ArrayList;
import java.util.List;

public class ValidationException extends Exception {

	private static final long serialVersionUID = 1L;

	private List<ValidationTraceElement> list;

	public ValidationException() {
		this.list = new ArrayList<ValidationTraceElement>();
	}

	public List<ValidationTraceElement> getElements() {
		return this.list;
	}

	public void add(ValidationTraceElement element) {
		if (element.getParams().getName() != null) {
			List<ValidationTraceElement> tempList = this.list;
			this.list = new ArrayList<ValidationTraceElement>();
			this.list.add(element);
			this.list.addAll(tempList);
		}
	}

	@Override
	public String getMessage() {
		return "JSON Validation Exception at: " + this.toString();
	}

	@Override
	public String toString() {
		String string = "";
		for (int i = 0; i < list.size(); i++) {
			ValidationParams params = list.get(i).getParams();
			if (i > 0 && params.isArray() == false) {
				string += ".";
			}
			if (params.isArray()) {
				string += "[" + params.getName() + "]";
			} else {
				string += params.getName();
			}
		}
		return string;
	}

}
