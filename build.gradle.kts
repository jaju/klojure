group = "org.msync"
version = "0.0.1-SNAPSHOT"

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.10"
    `java-library`
    signing
    `maven-publish`
    id("io.github.gradle-nexus.publish-plugin") version "1.2.0"
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://clojars.org/repo")
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    // This dependency is exported to consumers, that is to say found on their compile classpath.
    api("org.clojure:clojure:1.11.1")

    implementation("nrepl:nrepl:1.0.0")
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

nexusPublishing {
    repositories {
        sonatype()
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            pom {
                packaging = "jar"
                name.set("Clojure via Kotlin")
                description.set("A library to enable low-friction usage of Clojure in your Kotlin projects")
                url.set("https://github.com/jaju/klojure/")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("jaju")
                        name.set("Ravindra R. Jaju")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/jaju/klojure")
                    developerConnection.set("scm:git:ssh://github.com/jaju/klojure")
                }
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}

if (project.hasProperty("dev") &&
    project.property("dev") == "true" &&
    File("$rootDir/dev.gradle.kts").isFile) {
    apply("$rootDir/dev.gradle.kts")
}