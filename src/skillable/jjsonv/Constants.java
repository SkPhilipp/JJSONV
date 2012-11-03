package skillable.jjsonv;

import java.util.regex.Pattern;

/**
 * 
 * @author SkPhilipp
 *
 */
public final class Constants {

	/**
	 * We can use java.util.Scanner for reading it back correctly.
	 */
	public static final Character CHAR_DEPTH = '\t';
	public static final Character CHAR_NAMESPACE_BEGIN = '[';
	public static final Character CHAR_NAMESPACE_END = ']';
	public static final Character CHAR_TYPE = ':';

	public static final String STRING_NEWLINE = System.getProperty("line.separator");
	public static final String STRING_ARRAY = "[]";

	private static final String REGEX_DEPTH = "([\\t]*)";
	private static final String REGEX_NAMESPACE = "(\\[[a-z0-9\\-]+\\])?";
	private static final String REGEX_NAME = "([a-z0-9\\-]+)";
	private static final String REGEX_TYPE = "([a-z0-9\\-]+)";
	private static final String REGEX_ARRAY = "((\\[\\])*)";

	public static final Pattern LINE_PATTERN = Pattern.compile(String.format("^%s%s%s%c%s%s$", REGEX_DEPTH, REGEX_NAMESPACE, REGEX_NAME, CHAR_TYPE, REGEX_TYPE,
			REGEX_ARRAY));

	private Constants() {
	}

}
