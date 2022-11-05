FROM tomcat:8-jre8
LABEL "Project"="timecard"
LABEL "Author"="Ryuichi"

RUN rm -rf /usr/local/tomcat/webapps/*
COPY timecard.war usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh","run"]
WORKDIR /usr/local/tomcat/
VOLUME /usr/local/tomcat/webapps