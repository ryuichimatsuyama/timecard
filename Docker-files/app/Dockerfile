FROM tomcat:8-jre8
LABEL "Project"="timecard"
LABEL "Author"="Ryuichi"
ENV CATALINA_OPTS='-Duser.timezone=Asia/Tokyo'

RUN rm -rf /usr/local/tomcat/webapps/*
COPY timecard_docker.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh","run"]
WORKDIR /usr/local/tomcat/
VOLUME /usr/local/tomcat/webapps