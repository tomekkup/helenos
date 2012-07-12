SET MAVEN_OPTS=-Xrunjdwp:transport=dt_socket,address=4000,server=y,suspend=n
cls
mvn -o clean jetty:run
