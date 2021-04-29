FROM openjdk:11
LABEL authors="Gwendoline Dossegger <gwendoline.dossegger@heig-vd.ch>, Cassandre Wojciechowski <cassandre.wojciechowski@heig-vd.ch>"

COPY MockMock-1.4.0.one-jar.jar /opt/app/MockMock.jar

EXPOSE 2525
EXPOSE 8282

CMD ["java", "-jar", "/opt/app/MockMock.jar","-p","2525"]
