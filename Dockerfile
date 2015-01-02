# Example:
# docker build -t bkielczewski/akanke .
# docker run -rm -p 8080:8080 -v /data/logs /var/log bkielczewski/akanke

FROM dockerfile/java:oracle-java8

ENV version 1.0.0-SNAPSHOT

MAINTAINER Bartosz Kielczewski <bartosz@kielczewski.eu>

ADD ./akanke-web/target/akanke-web-1.0.0-SNAPSHOT.jar /data/
ADD ./akanke-site-example/target/config /data/config
ADD ./akanke-site-example/target/documents /data/documents
ADD ./akanke-site-example/target/resources /data/resources
ADD ./akanke-site-example/target/templates /data/templates

CMD ["java", "-jar", "akanke-web-${version}.jar"]

EXPOSE 8080