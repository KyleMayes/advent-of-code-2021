import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.6.0"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
}

repositories {
    mavenCentral()
}

group = "com.kylemayes.aoc2021"
version = "1.0.0"

dependencies {
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib"))
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.apache.commons:commons-math3:3.6.1")
    implementation("com.github.ajalt:mordant:1.2.1")
    implementation("com.google.guava:guava:31.0.1-jre")
    testImplementation(platform("org.junit:junit-bom:5.8.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "9"
}

tasks.test {
    useJUnitPlatform()
}
