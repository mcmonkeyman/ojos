
run:
	mvn package
	java -jar target/ojos-1.0-SNAPSHOT.jar server settings.yml

cleanup:
	ls -d -1 $$PWD/src/main/resources/assets/images/* | grep -v '1x1.png' | xargs rm

test:
	mvn test

format_check:
	mvn spotless:check

format:
	mvn spotless:apply

get:
	curl -vvv -X GET http://localhost:8080/capture/redirect?url=abc.com

post:
	curl -X POST -H "Content-Type: application/json" -d '{"url":"www.fun.com", "selector":"head"}' http://localhost:8080/capture/info

update_versions:
	mvn versions:use-latest-versions

see_logs:
	grep CapturaClient log/mylogger.log

PHONY: run cleanup test format_check format get post update_versions see_logs
