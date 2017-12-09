## CodeJohnny Open Source Java Code Generator

Version: 2.0.5
Updated: December 8, 2017

***Please see the [CodeJohnny Wiki](https://github.com/mintster/codejohnny/wiki) for complete documentation***

### About

CodeJohnny is a template-based Code Generation Application where you populate `{{mustache-like}}` tags in pure Java. CodeJohnny can generate code in any language for things like data retrieval, SQL Scripts and Procedures, POJOs and Builder patterns. 

CodeJohnny's XML templates support **Typed Properties**, **Variables**, **Methods** and **Mustache Parsing**. 
- **Typed Properties** like Booleans and Integers enable logical operations in our templates. 
- **Variables** are used to replace `{singleBracket}` tags and 
- **Methods** replace tags represented as`{{~doubleBracket}}` tags. 
- **Mustache Parsing** is performed last on `{{#doubleBracket}}{{/doubleBracket}}` tag groups. 
- **Built-in tags** are included, like `{pluraldataclass}`, `{primarykey}`, and others, with 
- **Global properties** defined in `globalproperties.xml.`

### Installation

CodeJohnny was originally created to generate JDBC boilerplate code, so it has a data-centric design a requires either a MySQL database used solely for Code Generation or CodeJohnny data objects added to your application's database. For either approach start by running the MySQL setup script located in `/install/sql.`

Next configure your MySQL Connection properties located in `/resources/connections.xml.`  You can optionally add CodeJohnny Global properties in `/resources/globalproperties.xml.`

Templates for CodeJohnny are located in the project root `/templates` directory.

### Generating Your First Template

Here's an overview on how to generate your first template. We'll start with creating a Plain Old Java Option, or POJO.

1. We've configured our MySQL Connection in `/resources/connections.xml`. Notice the`<name />` field, typically in the logical form of [SQLENGINE].[DB], or in our example `"mysql.codejohnnydb"` where "mysql" is the SQL Engine and "codejohnnydb" is the database.
2. We need a SQL Table for the POJO so we'll use the`CodeJohnny_Users` table included in the setup script.
3. The Template for our POJO is `/templates/java/pojo/pojo.xml.` We open the .XML template, enter `User` as our dataclass, `codejohnny_users` as our table, and the connections.xml `<name />` value we entered above, `mysql.codejohnnydb`.
4. Finally, we enter our Template location in our `Launcher` class as our `template` value, or `java/pojo/pojo.xml.`
5. When we run the app in our IDE the `User` POJO magically appears in our output window, which we would then copy-paste into a new class file.

### Set External Resources and Templates To Run As Jar

By default templates are located in the project root `/templates` folder and configuration files in `/resources`. To Run as a Jar you will need to configure both *TEMPLATES* and *CONFIG* external folders. Set the root path to those external directories in your `codejohnny.properties` *codejohnny.directory* property. In the example the entry would look like this:

`codejohnny.directory=/home/daveburke/codejohnny/`

where the contents of the `codejohnny` folders would look like this:

```bash
/home/daveburke/codejohnny/config
    - connections.xml
    - globalproperties.xml
/home/daveburke/codejohnny/templates/
    - templates from project root /templates 
```

Then create your JAR with Gradle. We're creating an Executable or "Fat" Jar so enter the following

```bash
$ gradle clean fatJar
```

The JAR will be located in your Project `build/libs` folder. Copy to the location of your chosing. To run enter the template you wish to execute on the command line based on its location relative to your `/codejohnny/templates` directory:

```bash
$ java -jar codejohnny.jar java/pojo/builder.xml
```

### Building CodeJohnny

CodeJohnny is built with Gradle, so simply build as usual with `$ gradle clean build` to get started, or build CodeJohnny in your IDE.

To create an Executable Jar use the`fatJar` Gradle task, or `$ gradle clean build fatJar.`

### License

CodeJohnny is licensed under the Creative Commons Attribution NonCommercial NoDerivs (CC-NC-ND) v3.0 license. See **LICENSE.md** for details.

### Support 

CodeJohnny comes with no support of any kind. If you have questions or comments, please use the contact form at nixmash.com.

### Changelog

### v 2.0.5 - December 8, 2017

- **CodeJohnny** repository goes public with full Engine and Client source code.

### v 2.0.4 - September 18, 2017

- **Mustache Parsing** now supported for object list tags `{{# objects}}...{{/ objects}}` 
- `{{~ methodName }}` is the new tag format for Methods

### v 2.0.3 - September 15, 2017

- `dbexecute.xml` for executing JDBC in Jangles Method
-  `type=java.lang.String` removed from Template `<Properties />`. `type` only required for *Boolean* and *Integer* types. Will not parse Template {tags} if present.
 
### v 2.0.2 - September 13, 2017

- `dbretrieve.xml` improvements with `isCallableStatement` option
- Integer CodeJohnny Template Property Type now supported

### v 2.0.1 - September 8, 2017

- `coltypeswitch.xml` template to create CodeJohnny Template Methods that loops over SqlColumnType
- Improved README

### v 2.0.0 - September 4, 2017

- Guice Redesign
- Logical Operators added to Templates as Java Type `Ex: if (addPrimaryId)...`
- Better Local and External Configuration switching
- `dbinsertproc.xml` template for entering data with MySQL Stored Proc
- `insertproc.xml` template to generate the MySQL Stored Proc
- `builder.xml` template for creating POJO Builder Pattern in Java

### v1.5.0 - August 24, 2017

- Fix for `DataColumn.IsRequired` to determine if field is nullable - updated stored proc `p_codejohnny_columns_get`
- `mysqlInsert.xml` template for creating and returning new DTO objects into MySQL


*HAPPY CODE GENERATING!*


