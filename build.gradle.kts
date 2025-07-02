plugins {
    kotlin("jvm") version "2.1.10"
    id("io.ktor.plugin") version "3.2.0"
}

group = "com.example"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:3.2.0")
    implementation("io.ktor:ktor-server-netty-jvm:3.2.0")
    implementation("io.ktor:ktor-server-websockets-jvm:3.2.0")
    implementation("io.ktor:ktor-server-config-yaml-jvm:3.2.0")
    implementation("ch.qos.logback:logback-classic:1.4.14")
    testImplementation("io.ktor:ktor-server-test-host-jvm:3.2.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:2.1.10")
}