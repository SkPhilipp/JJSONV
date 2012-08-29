JJSONV
======

Java JSON Validator
- Requires Jackson ASL 1.9.7 Core & Mapper

Schemas
-------
Speaks for itself, the only array type supported as of now is Object[]
Object content is detected by _tabs_, do not use more tabs than you should use ( think python )
```
model:Object
	name:String
	public:Boolean
	members:Object[]
		name:String
		size:Int
pages:Object[]
	design:String
```
Validating
==========
```java

	SchemaParser parser = new SchemaParser();
	ObjectMapper mapper = new ObjectMapper();

	SchemaValidator schemaValidator = parser.load(new File("schema.jsons"));
	try {
		schemaValidator.validate(mapper.readTree(new File("data.json")));
	} catch(ValidationException e) {
		// Invalid JSON!
	}

```
Tracing
-------
You can see where validation errors happen, this is pretty useful when returning sending it back to a JavaScript web application.
```java
	try {
		schemaValidator.validate(mapper.readTree(new File("false-data.json")));
	} catch(ValidationException e) {
		// Will print something like "object.members[0][1].inner[4].size"
		// Depends on what you're validating of course.
		System.out.println(e);
		// You can use e.getElements() for even deeper inspection
	}


```
Custom Element Validators
=========================
In SchemaParser the following standard validators are already registered;
```java
	public SchemaParser() {
		...
		this.add("String", StringValidator.class);
		this.add("Bool", BooleanValidator.class);
		this.add("Boolean", BooleanValidator.class);
		this.add("Int", IntValidator.class);
		this.add("Integer", IntValidator.class);
		...
	}
```
You can register your own custom validators at a SchemaParser, the SchemaParser
will then create instances of these validators when they are used in a schema file.
For example:
```java
	SchemaParser.add("Thing", MyThingValidator.class);
```
Simple example of a validator that accepts JSON values that are integers or strings
```java
public class IntOrStringValidator extends ElementValidator {

	@Override
	public boolean ok(JsonNode node, ValidationContext context) {
		return node.isInt() || node.isTextual();
	}

}
```
Validation Context
==================
ValidationContext is data that is passed to every ElementValidator, the ElementValidators can read from it
and add more data to it - It's very useful for expensive loads such as a custom "UserValidator", so that after
checking if your User objects exists in the database, and obtaining a reference to it; You can then store that
already loaded data into the ValidationContext. The ValidationContext is returned
by ```java SchemaValidator.validate(JsonNode)```, you can then read all your data from the ValidationContext.

Here's an example of a validator that only accepts existing "members" -- See also: test/tests/TestValidator.java
( This validator will create a map to store data it's data in - note that it doesn't check 
wether or not the "member" was already loaded into the ValidationContext )
```java
public class TestValidator extends ElementValidator {

	@Override
	public boolean ok(JsonNode node, ValidationContext context) {
		// Check for valid data
		if (node.isTextual() == false)
			return false;
		// Load up our existing member
		String key = node.getTextValue();
		String value = MyDatabase.expensiveMemberLoad(key);
		if (value == null)
			return false;
		// Get or create our map
		@SuppressWarnings("unchecked")
		Map<String, String> members = (Map<String, String>) context
				.get("Members");
		if (members == null)
			members = new HashMap<String, String>();
		// Add our member which we know exists
		members.put(key, value);
		// Store our members again
		context.put("Members", members);
		return true;
	}

}
```
Example on how to use the ValidationContext -- See also: test/tests/Tests.java
```java
	SchemaParser parser = new SchemaParser();
	parser.add("Test", TestValidator.class);
	SchemaValidator validator = parser.load(basicCustomSchema);
	ValidationContext context = validator.validate(mapper.readTree(basicJson));
	@SuppressWarnings("unchecked")
	Map<String, String> members = (Map<String, String>) context.get("Members");
	assertEquals("Data1", members.get("Member1"));
	assertEquals("Data2", members.get("Member2"));
```