val postgresVersion: String by project
val flywayVersion: String by project
val openApiVersion: String by project

plugins {
    java
    id("org.springframework.boot") version "3.1.2"
}

repositories {
    mavenCentral()
}

dependencies {
    // tady je pak dobré ve větších projektech používat proměnné pro verze. Například tady je vidět, že Spring je tu ve verzi 3.1.2
    val springVersion = "3.1.2"
    implementation("org.springframework.boot:spring-boot-starter-web:$springVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springVersion")
    // nebo se to taky občas definuje globálně v gradle.properties - to je na tobě, jak se ti to dělá líp.
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$openApiVersion")
    runtimeOnly("org.postgresql:postgresql:$postgresVersion")
    implementation("org.flywaydb:flyway-core:$flywayVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springVersion")
}

group = "com.bezdekova"
version = "0.0.1-SNAPSHOT"
description = "books"
java.sourceCompatibility = JavaVersion.VERSION_17

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}

tasks.withType<Test> {
    useJUnitPlatform()
}
