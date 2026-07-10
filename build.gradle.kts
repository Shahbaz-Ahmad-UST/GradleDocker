import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.testing.Test

plugins {
    java
    id("io.qameta.allure") version "2.12.0"
}

group = "com.ust.sdet"
version = "0.1.0"

repositories {
    mavenCentral()
}

val seleniumVersion = "4.45.0"
val selenideVersion = "7.16.2"
val junitVersion = "5.14.4"
val cucumberVersion = "7.34.3"
val allureVersion = "2.33.0"
val extentVersion = "5.1.2"
val extentCucumberAdapterVersion = "1.14.0"
val slf4jVersion = "2.0.17"
val testcontainersVersion = "1.21.3"
val flywayVersion = "10.22.0"
val mysqlVersion = "9.4.0"

java {
    sourceCompatibility = JavaVersion.VERSION_22
    targetCompatibility = JavaVersion.VERSION_22
}

dependencies {

    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation(platform("io.cucumber:cucumber-bom:$cucumberVersion"))
    testImplementation(platform("io.qameta.allure:allure-bom:$allureVersion"))
    testImplementation(platform("org.testcontainers:testcontainers-bom:$testcontainersVersion"))

    testImplementation("org.seleniumhq.selenium:selenium-java:$seleniumVersion")
    testImplementation("com.codeborne:selenide:$selenideVersion")

    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.platform:junit-platform-suite")

    testImplementation("io.cucumber:cucumber-java")
    testImplementation("io.cucumber:cucumber-junit-platform-engine")
    testImplementation("io.cucumber:cucumber-picocontainer")

    testImplementation("io.qameta.allure:allure-cucumber7-jvm")
    testImplementation("io.qameta.allure:allure-junit5")

    testImplementation("com.aventstack:extentreports:$extentVersion")
    testImplementation("tech.grasshopper:extentreports-cucumber7-adapter:$extentCucumberAdapterVersion")

    testImplementation("org.slf4j:slf4j-simple:$slf4jVersion")

    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:mysql")

    testImplementation("org.flywaydb:flyway-core:$flywayVersion")
    testImplementation("org.flywaydb:flyway-mysql:$flywayVersion")

    testImplementation("com.mysql:mysql-connector-j:$mysqlVersion")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(22)
}

fun Test.useProjectTestClasses() {
    testClassesDirs = sourceSets.test.get().output.classesDirs
    classpath = sourceSets.test.get().runtimeClasspath
}

tasks.withType<Test>().configureEach {

    useJUnitPlatform()

    maxParallelForks = 1

    systemProperty("baseUrl", System.getProperty("baseUrl", "http://localhost:5173"))
    systemProperty("headless", System.getProperty("headless", "false"))
    systemProperty("build.label", System.getProperty("build.label", "local"))
    systemProperty("allure.results.directory", System.getProperty("allure.results.directory", "build/allure-results"))

    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.test {
    description = "Runs all tests."
    group = "verification"
}

val CatalogPomTest by tasks.registering(Test::class) {

    description = "Runs CatalogPomTest"
    group = "verification"

    useProjectTestClasses()

    include("**/CatalogPomTest.class")

    maxParallelForks = 1
}

val orderSuite by tasks.registering(Test::class) {
    description = "Runs Exercise1-3 and Milestone tests together"
    group = "verification"
    useProjectTestClasses()
    include("**/Exercise1Test.class", "**/Exercise2Test.class", "**/Exercise3Test.class", "**/MilestoneTest.class")
    maxParallelForks = 1
}

val exercise1Test by tasks.registering(Test::class) {
    description = "Runs Exercise1Test"
    group = "verification"

    useProjectTestClasses()

    include("**/Exercise1Test.class")
    maxParallelForks = 1
}

val exercise2Test by tasks.registering(Test::class) {
    description = "Runs Exercise2Test"
    group = "verification"

    useProjectTestClasses()

    include("**/Exercise2Test.class")
    maxParallelForks = 1
}

val exercise3Test by tasks.registering(Test::class) {
    description = "Runs Exercise3Test"
    group = "verification"

    useProjectTestClasses()

    include("**/Exercise3Test.class")
    maxParallelForks = 1
}

val milestoneTest by tasks.registering(Test::class) {
    description = "Runs MilestoneTest"
    group = "verification"

    useProjectTestClasses()

    include("**/MilestoneTest.class")
    maxParallelForks = 1
}

val allureReportInsightTest by tasks.registering(Test::class) {
    description = "Runs AllureReportInsightTest"
    group = "verification"

    useProjectTestClasses()

    include("**/AllureReportInsightTest.class")
    maxParallelForks = 1
}