FROM java:8u66-jdk

MAINTAINER shabutora<tsukasa@iyher.net>

ADD ./paraponera.zip /opt/paraponera.zip

RUN unzip /opt/paraponera.zip -d /opt
RUN rm /opt/paraponera.zip

EXPOSE 8080

CMD /opt/paraponera/bin/paraponera
