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

	ObjectValidator validator = parser.load(new File("schema.jsons"));
	try {
		validator.validate(mapper.readTree(new File("data.json")));
	} catch(ValidationException e) {
		// Invalid JSON!
	}

```
Tracing
-------
You can see where validation errors happen, this is pretty useful when returning sending it back to a JavaScript web application.
```java

	SchemaParser parser = new SchemaParser();
	ObjectMapper mapper = new ObjectMapper();

	ObjectValidator validator = parser.load(new File("schema.jsons"));
	try {
		validator.validate(mapper.readTree(new File("data.json")));
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
import org.codehaus.jackson.JsonNode;

public class IntOrStringValidator extends ElementValidator {

	@Override
	public boolean ok(JsonNode node) {
		return node.isInt() || node.isTextual();
	}

}
```
Example of a validator that only accepts existing "users"
```java
import org.codehaus.jackson.JsonNode;

public class UserIdValidator extends ElementValidator {

	@Override
	public boolean ok(JsonNode node) {
		if(node.isInt()){
			int userId = node.getIntValue();
			if(MyDatabase.userExists(userId)){
				return true;
			}
		}
		return false;
	}

}
```