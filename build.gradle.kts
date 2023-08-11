plugins {
    java
    id("org.springframework.boot") version "3.1.2"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:3.1.2")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.1.2")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
    runtimeOnly("org.postgresql:postgresql:42.6.0")
    implementation("org.flywaydb:flyway-core:9.16.3")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.2")
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
