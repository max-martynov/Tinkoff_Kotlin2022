import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.tasks.testing.logging.TestLogEvent.*


plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
    id("org.springframework.boot") version "2.6.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

group = "me.maksimmartynov"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.6")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("org.jetbrains.exposed:exposed-spring-boot-starter:0.38.2")
    implementation("org.jetbrains.exposed:exposed-core:0.38.2")
    implementation("com.h2database:h2:2.1.210")
    implementation("org.liquibase:liquibase-core:4.9.0")

    implementation("org.springframework.boot:spring-boot-starter-activemq:2.6.6")

    testImplementation(kotlin("test"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.ninja-squad:springmockk:3.1.1")
    testImplementation("io.mockk:mockk:1.12.3")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test>() {
    useJUnitPlatform()
    testLogging {
        events(FAILED, PASSED, SKIPPED)
        showExceptions = true
        showCauses = true
    }
}
