
# Ojos
A http service that calls an image capturing service captura. Based on [requirements](./docs/requirements.md)


# Requirements
* Maven
* Java 1.8

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
