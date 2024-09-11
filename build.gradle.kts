group = "org.msync"
version = "0.0.3-SNAPSHOT"

plugins {
    java
    signing
    `maven-publish`
    `kotlin-dsl`
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
}

// If project has the "dev" property, then apply the dev plugin
// This is useful for local development
if (project.hasProperty("dev") && project.property("dev") == "true") {
    apply(plugin = "dev")
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
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

sourceSets {
    main {
        resources {
            srcDirs += srcDir("src/main/clojure")
        }
    }

    test {
        resources {
            srcDirs += srcDir("src/test/clojure")
        }
    }
}

dependencies {
    // This dependency is exported to consumers, that is to say found on their compile classpath.
    api("org.clojure:clojure:1.12.0")
    api("org.slf4j:slf4j-api:2.0.16")

    //
    implementation("nrepl:nrepl:1.3.0")

    // Test
    testImplementation("cheshire:cheshire:5.13.0")
}


testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            // Use Kotlin Test test framework
            useKotlinTest("2.0.20")
        }
    }
}

nexusPublishing {
    this.repositories {
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
                    url.set("https://github.com/jaju/klojure")
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
