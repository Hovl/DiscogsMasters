FROM maven:3.9-eclipse-temurin-8 AS build
WORKDIR /app
COPY pom.xml .
COPY common/pom.xml common/
COPY web/pom.xml web/
COPY worker/pom.xml worker/
RUN mvn dependency:resolve -pl common,web -q 2>&1 || true
COPY common/ common/
COPY web/ web/
COPY worker/ worker/
RUN mvn package -pl common,web -am -DskipTests -q

FROM eclipse-temurin:8-jre
WORKDIR /app
COPY --from=build /app/web/target/repo/ repo/
COPY --from=build /app/web/target/classes/ classes/
COPY --from=build /app/web/src/main/webapp/ web/src/main/webapp/
EXPOSE 8080
ENV PORT=8080
CMD ["sh", "-c", "java -cp classes:$(find repo -name '*.jar' -o -name '*.war' | tr '\\n' ':') ebs.web.Boot"]
