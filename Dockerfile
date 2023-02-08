FROM azul/zulu-openjdk-alpine:17 AS builder

WORKDIR /aitum

COPY . .
RUN ./gradlew --no-daemon :build

FROM azul/zulu-openjdk-alpine:17-jre

WORKDIR /aitum
COPY --from=builder /aitum/build/libs/aitum-ifttt*.jar ./server.jar

CMD ["java", "-jar", "server.jar"]
