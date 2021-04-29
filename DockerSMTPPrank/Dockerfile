FROM openjdk:11
LABEL authors="Gwendoline Dossegger <gwendoline.dossegger@heig-vd.ch>, Cassandre Wojciechowski <cassandre.wojciechowski@heig-vd.ch>"

COPY SMTP-1.0-SNAPSHOT.jar /opt/app/SMTPPrank.jar

COPY config /opt/app/config

WORKDIR /opt/app

CMD ["java", "-jar", "/opt/app/SMTPPrank.jar"]
