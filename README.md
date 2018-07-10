
# Ojos
A http service that calls an image capturing service captura. Based on [requirements](./docs/requirements.md)

Take a look at [tasks](./docs/tasks.md) to see my thought process.

# Requirements
* Maven
* Java 10

# Test
```
mvn test
```

# Run
```
mvn package
java -jar target/ojos-1.0-SNAPSHOT.jar server settings.yml
```

# Format
```
mvn spotless:check
mvn spotless:apply
```

# About
Based on 
```
mvn archetype:generate -DarchetypeGroupId=io.dropwizard.archetypes -DarchetypeArtifactId=java-simple -DarchetypeVersion=0.9.1
```
