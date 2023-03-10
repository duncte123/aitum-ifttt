plugins {
    application

    kotlin("jvm") version "1.8.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "me.duncte123"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.mongodb:mongodb-driver-sync:4.8.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.0")
    implementation("org.slf4j:slf4j-simple:2.0.3")
    implementation("io.javalin:javalin:5.3.2")
}

application {
    mainClass.set("me.duncte123.iftttAitum.MainKt")
}

tasks.shadowJar {
    archiveBaseName.set("aitum-ifttt")
    archiveClassifier.set("prod")
}

tasks.test {
    useJUnitPlatform()
}

tasks.wrapper {
    gradleVersion = "7.6"
}

kotlin {
    jvmToolchain(17)
}
