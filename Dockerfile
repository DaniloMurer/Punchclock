FROM tomcat:8.0-alpine

# Expose SSH Port 22, HTTP Port 80 and Tomcat Port 8080
EXPOSE 8080

ADD build/libs/punchclock-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps

CMD ["catalina.sh", "run"]


