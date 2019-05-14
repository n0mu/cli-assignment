# CLI Server Manager Assignment


This CLI is used to make CRUD operations to a H2 database. 
The first time that a CRUD operation is executed the database is created
 in the same folder with name server_manager.mv.db' . This database will
 retain the state of the application. 


## Installation 
Compile with maven
```
mvn clean install
```
**Requirements**: 
JDK 8

## Use 
Execute ``java -jar server-manager.jar`` and select the desired option


**Invoke help**
```
java -jar server-manager.jar help

Usage:  [-hV] [COMMAND]
Manages Server Registry
  -h, --help      Show this help message and exit.
  -V, --version   Print version information and exit.

Commands:

The available commands are:
  help    Displays help information about the specified command
  add     Add a new server
  load    Load a new server from xml file
  edit    Edit server
  delete  Delete a server
  list    List servers
  count   Count servers
```  

**Add server**  
```
java-jar target/server-manager.jar add 99 tomcatI3 payments_backend

Server Added With id=99 name=tomcatI3 description=payments_backend
```

**Delete server**
```
java -jar target/server-manager.jar delete 99

Server Deleted With id=99
```

**Load servers from XML**
The application can load a list of servers contained in an XML file. 
The XSD is in _src/main/resources/xml/servers.xsd_

``` xml
<?xml version="1.0" encoding="UTF-8"?>
<servers>
    <server>
        <id>99</id>
        <name>tomcat12</name>
        <description>payments backend</description>
    </server>
    ...
</servers>

``` 

## Server Data 
Server are described by three fields:
- **id** string, 50 chars max, required, doesn't accept duplicates
- **name** string, 50 chars max, required, doesn't accept duplicates
- **description** string, 200 chars max

## Design Overview
### Components
**[Picocli](https://picocli.info/) Commands:**  
The commands are in the _cli_ package. 
Each command handles the parameters and executes the parsing and 
the errors associated. The commands extend _Runnable_, _run_ method contains
the business logic.

**Server Repository:**
The server repository is a CRUD implementation using [jdbi](http://jdbi.org/) in top of an 
[H2](https://www.h2database.com) database that persist in a file
in the same folder that the command its executed. 
All the classes are in the _db_ package.  

**XML Parser:**
The _load_ method accept XML files as entry. Parsing is executed with the 
library [dom4j](https://dom4j.github.io/) and validated with the schema 
_src/main/resources/xml/servers.xsd_ . 

### Error Handling
- Parsing errors are handled by the picocli classes transparently.
- All business errors (_error_ package) extend Runtime exceptions and are handled by 
logic inside the _run_ method of each command.


