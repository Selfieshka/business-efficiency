FROM gradle:8.10-jdk21 AS build

WORKDIR /workspace/app

COPY ../build.gradle settings.gradle gradle.properties ./

COPY ../be-db/src ./be-db/src
COPY ../be-api/src ./be-api/src
COPY ../be-impl/src ./be-impl/src
COPY ../be-db/build.gradle ./be-db
COPY ../be-api/build.gradle ./be-api
COPY ../be-impl/build.gradle ./be-impl

RUN gradle clean assemble --stacktrace --parallel --console=plain

RUN mkdir -p be-impl/build/dependency && (cd be-impl/build/dependency; jar -xf ../libs/*.jar)

FROM eclipse-temurin:21-jre-alpine

ARG DEPENDENCY=/workspace/app/be-impl/build/dependency

COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

EXPOSE 8080

ENTRYPOINT ["java", "-cp", "app:app/lib/*", "com.technokratos.kirillakhmetov.BusinessEfficiencyApplication"]