import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.tasks.testing.logging.TestExceptionFormat.*
import org.gradle.api.tasks.testing.logging.TestLogEvent.*


plugins {
    kotlin("jvm") version "1.6.10"
    application
}

group = "me.maksimmartynov"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:1.12.3")
}

application {
    mainClass.set("MainKt")
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
