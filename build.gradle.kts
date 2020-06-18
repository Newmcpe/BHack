plugins {
    java
    kotlin("jvm") version "1.3.72"
}

group = "ru.newmcpe"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("net.java.dev.jna","jna","5.5.0")
    implementation("net.java.dev.jna","jna-platform","5.5.0")
    implementation("org.jire.arrowhead","arrowhead","1.3.3")
    implementation("com.github.jonatino:Java-Memory-Manipulation:2.1.2")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}