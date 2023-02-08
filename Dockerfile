FROM azul/zulu-openjdk-alpine:19 AS builder

WORKDIR /aitum

COPY . .
RUN ./gradlew --no-daemon :build

FROM azul/zulu-openjdk-alpine:19-jre

WORKDIR /aitum
COPY --from=builder /aitum/build/libs/aitum-ifttt*.jar ./server.jar

CMD ["java", "-jar", "server.jar"]
