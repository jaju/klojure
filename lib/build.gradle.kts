group = "org.msync"
version = "0.0.1-SNAPSHOT"

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.8.10"

    // Apply the java-library plugin for API and implementation separation.
    `java-library`
    `maven-publish`
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenLocal()
    mavenCentral()
}

dependencies {
    // This dependency is exported to consumers, that is to say found on their compile classpath.
    api("org.clojure:clojure:1.11.1")

    // Test
    testImplementation("cheshire:cheshire:5.11.0")
}

testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            // Use Kotlin Test test framework
            useKotlinTest("1.8.10")
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = rootProject.name
            pom {
                name.set("Clojure via Kotlin")
                description.set("A library to enable low-friction usage of Clojure in your Kotlin projects")
            }
            from(components["java"])
        }
    }
    repositories {
        mavenLocal()
    }
}