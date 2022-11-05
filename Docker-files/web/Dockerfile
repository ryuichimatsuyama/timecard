FROM nginx
LABEL "Project"="timecard"
LABEL "Author"="Ryuichi"

RUN rm -rf /etc/nginx/conf.d/default.conf
COPY nginxtimecard.conf /etc/nginx/conf.d/timecard.conf