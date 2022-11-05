FROM mysql:5.7.31
LABEL "Project"="timecard"
LABEL "Author"="Ryuichi"

ENV MYSQL_ROOT_PASSWORD="timepass"
ENV MYSQL_DATABASE="timecard"

ADD timecard_dump.sql docker-entrypoint-initdb.d/timecard_dump.sql