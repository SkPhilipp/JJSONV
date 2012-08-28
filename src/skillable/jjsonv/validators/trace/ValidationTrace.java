package skillable.jjsonv.validators.trace;

import java.util.ArrayList;
import java.util.List;

public class ValidationTrace {

	private final List<ValidationTraceElement> list;

	public List<ValidationTraceElement> getTrace() {
		return this.list;
	}

	public ValidationTrace() {
		this.list = new ArrayList<ValidationTraceElement>();
	}

	public void add(ValidationTraceElement element) {
		if (element.getParams().getName() != null) {
			this.list.add(element);
		}
	}

	public void pop() {
		if (this.list.size() > 0) {
			this.list.remove(this.list.size() - 1);
		}
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