plugins {
    java
    kotlin("jvm") version "1.3.72"
}

group = "ru.newmcpe"
version = "1.0"

repositories {
    mavenCentral()
}


val jar by tasks.getting(Jar::class) {
    manifest {
        attributes["Main-Class"] = "Main"
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("net.java.dev.jna","jna","5.5.0")
    implementation("net.java.dev.jna","jna-platform","5.5.0")
    implementation("org.jire.arrowhead","arrowhead","1.3.3")
    implementation("it.unimi.dsi","fastutil","8.3.1")
    implementation("com.github.jonatino:Java-Memory-Manipulation:2.1.2")
    implementation("org.apache.commons","commons-lang3","3.10")
    implementation("com.1stleg","jnativehook","2.1.0")

    implementation("com.badlogicgames.gdx","gdx","1.9.10")
    implementation("com.badlogicgames.gdx","gdx-box2d","1.9.10")
    implementation("com.badlogicgames.gdx","gdx-backend-lwjgl","1.9.10")
    implementation("com.badlogicgames.gdx:gdx-platform:1.9.10:natives-desktop")
    implementation("com.badlogicgames.gdx:gdx-box2d-platform:1.9.10:natives-desktop")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    jar {
        doFirst {
            from({
                configurations.runtimeClasspath.get().filter { !it.name.endsWith("pom") }
                    .map { if (it.isDirectory) it else zipTree(it) }
            })
        }
        destinationDirectory.set(File("$rootDir/artifacts"))
    }
}
sourceSets {
    main {
        resources {
            srcDirs("src/main/resources", "src/main/configs")
        }
    }
}