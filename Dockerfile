FROM eclipse-temurin:17

LABEL author="Xiyuan Tu"

WORKDIR /app

COPY target/blog-0.0.1-SNAPSHOT.jar /app/blog-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "blog-0.0.1-SNAPSHOT.jar"]