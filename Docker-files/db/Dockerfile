FROM mysql:5.1.45
LABEL "Project"="timecard"
LABEL "Author"="Ryuichi"

ENV MYSQL_ROOT_PASSWORD="mespass"
ENV MYSQL_DATABASE="mesuser"

ADD timecard_dump.sql docker-entrypoint-initdb.d/timecard_dump.sql