# MessangerDB

In this project I learn how to connect to data base and make some requests from java.

## Idea

The main idea of this project was to try to connect to DB and emulating, saving, update, searching for messages or users in the database. All this functions you can try in class edu.school21.chat.app.Main.

## Stack

1. Assembly system:
    
    `Maven`

2. Interaction with the DB

    `HikariCP`: for transfer connection pool

    `PostgreSQL core`: for connections and making requests

## Installation

For installation you need to raise the DB similar to DB in resources (data.sql, schema.sql) and change properties.properties and then run command from Chat dir:
``` 
mvn install
java -jar target/Chat-5.0-SNAPSHOT.jar
```
