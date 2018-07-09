
run:
	mvn package
	java -jar target/ojos-1.0-SNAPSHOT.jar server settings.yml

test:
	mvn test

format:
	mvn spotless:apply

get:
	curl -vvv -X GET http://localhost:8080/capture/redirect?url=abc.com

post:
	curl -X POST -H "Content-Type: application/json" -d '{"url":"www.fun.com", "selector":"head"}' http://localhost:8080/capture/info


PHONY: run format post get
