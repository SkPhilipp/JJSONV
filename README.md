JJSONV
======

Java JSON Validator
- Requires Jackson ASL 1.9.7 Core & Mapper

Schemas
-------
It simply speaks for itself, how amazingly easy to use!

Object content is detected by _tabs_, do not use more tabs than you should use.
```
model:object
	name:string
	public:boolean
	members:object[]
		name:string
		size:int
pages:object[]
	design:string
```
_note_: You can nest arrays as deep as you want!
Validating
==========
```java
SchemaFactory factory = new SchemaFactory();
ObjectMapper mapper = new ObjectMapper();
Schema schema = factory.create(new File("schema.jsons"));
try {
	schema.validate(mapper.readTree(new File("data.json")));
} catch(ValidationException e) {
	// Invalid JSON!
}
```
Tracing
-------
You can see where validation errors happen, this is incredibly useful when sending it back to a JavaScript web application.
```java
SchemaFactory factory = new SchemaFactory();
ObjectMapper mapper = new ObjectMapper();
Schema schema = factory.create(new File("schema.jsons"));
try {
	schema.validate(mapper.readTree(new File("false-data.json")));
} catch(ValidationException e) {
	// Will print something like "object.members[0][1].inner[4].size"
	// Depends on what you're validating of course.
	System.out.println(e);
	// You can use e.getElements() for even deeper inspection
}
```
Custom Element Validators
=========================
A SchemaFactory has the following standard validators preregistered;
```java
public SchemaFactory() {
	validators = new HashMap<String, Class<? extends Validator>>();
	validators.put("string", StringValidator.class);
	validators.put("bool", BooleanValidator.class);
	validators.put("boolean", BooleanValidator.class);
	validators.put("int", IntValidator.class);
	validators.put("integer", IntValidator.class);
	validators.put("object", PlainObjectValidator.class);
}
```
You can register your own custom validators at a SchemaFactory, the SchemaFactory
will then create instances of these validators when they are used in a schema file.
Here's a simple example of a validator that accepts JSON values that are integers or strings
```java
public class IntOrStringValidator extends ElementValidator {

	@Override
	public boolean ok(JsonNode node, ValidationContext context) {
		return node.isInt() || node.isTextual();
	}

}
```
And this is how you can regiser it!
```java
SchemaFactory factory = new SchemaFactory();
factory.add("thing", IntOrStringValidator.class);
```
Easy right? You should extend either ```com.pseudocoding.jjsonv.validators.ElementValidator``` or ```com.pseudocoding.jjsonv.validators.ObjectValidator```
Validation Context
==================
ValidationContext is data that is passed to every Validator, the Validators can read from it
and add more data to it - It's very useful for expensive loads such as a custom "UserValidator", so that after
checking if your User objects exists in the database, and obtaining a reference to it; You can then store that
already loaded data into the ValidationContext. The ValidationContext is returned
by ```Schema.validate(JsonNode)```, you can then read all your data from the ValidationContext.

Here's an example of a validator that only accepts existing "members" -- See also: test/tests/TestValidator.java
( This validator will create a map to store data it's data in - note that it doesn't check 
wether or not the "member" was already loaded into the ValidationContext )
```java
public class TestValidator extends ElementValidator {

	private final Map<String, String> existingMembers;

	public TestValidator() {
		existingMembers = new HashMap<String, String>();
		existingMembers.put("Member1", "Data1");
		existingMembers.put("Member2", "Data2");
	}

	@Override
	public boolean ok(JsonNode node, ValidationContext context) {
		// Check for valid data
		if (node.isTextual() == false)
			return false;
		// Load up our existing member
		String key = node.getTextValue();
		String value = existingMembers.get(key);
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
```
// Load custom schema
Schema schema = factory.create(basicCustomSchema);
// Get the result of validation
ValidationContext context = schema.validate(mapper.readTree(basicJson));
@SuppressWarnings("unchecked")
Map<String, String> members = (Map<String, String>) context.get("Members");
for(Map.Entry<String, String> entry : members.entrySet()){
	// Do something with loaded data
}
```