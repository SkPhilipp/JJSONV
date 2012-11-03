package skillable.jjsonv;

import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;
import java.util.regex.Matcher;


/**
 * 
 * @author SkPhilipp
 *
 */
public class SchemaParser {

	/**
	 * Reads a schema from a reader, and converts it to SchemaElements.
	 * @return top elements in schema
	 * @throws SchemaException when an illegally formatted schema is read.
	 */
	public SchemaElement[] load(Reader reader) throws SchemaException, IOException {

		Scanner scanner = new Scanner(reader);
		SchemaElement originalTop = new SchemaElement();
		SchemaElement currentTop = originalTop;

		int currentDepth = 0;
		int lineCount = 0;

		while (scanner.hasNextLine()) {

			lineCount++;

			String line = scanner.nextLine();

			final Matcher matcher = Constants.LINE_PATTERN.matcher(line);
			if (!matcher.find()) {
				throw new SchemaException(String.format("Syntax error at line %d ( \"%s\" )", lineCount, line));
			}

			String groupIndent = matcher.group(1);
			String groupNamespace = matcher.group(2);
			String groupName = matcher.group(3);
			String groupType = matcher.group(4);
			String groupArray = matcher.group(5);

			int indentDepth = groupIndent.length();
			int arrayDepth = groupArray.length() / Constants.STRING_ARRAY.length();

			SchemaElement latest = new SchemaElement(groupNamespace, groupName, groupType, arrayDepth, currentTop);

			while (indentDepth < currentDepth) {
				currentTop = currentTop.getParent();
				currentDepth--;
			}

			if (indentDepth == currentDepth + 1) {
				currentTop = currentTop.getChildren().get(currentTop.getChildren().size() - 1);
				currentDepth++;
			} else if (indentDepth > currentDepth + 1) {
				throw new SchemaException(String.format("Too much indentation at line %d", lineCount));
			}

			currentTop.addChild(latest);

		}

		return originalTop.getChildren().toArray(new SchemaElement[0]);

	}

}
