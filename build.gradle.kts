group = "org.msync"
version = "0.0.1-SNAPSHOT"

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.10"
    signing
    `maven-publish`
    id("io.github.gradle-nexus.publish-plugin") version "1.2.0"
}

nexusPublishing {
    repositories {
        sonatype {  //only for users registered in Sonatype after 24 Feb 2021
            nexusUrl.set(uri("https://oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://oss.sonatype.org/content/repositories/snapshots/"))
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "org.msync"
            artifactId = rootProject.name
            pom {
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
            from(components["kotlin"])
        }
    }
}

signing {
    sign(publishing.publications["maven"])
}

subprojects {
    project(":lib")
}

if (project.hasProperty("dev") &&
    project.property("dev") == "true" &&
    File("$rootDir/dev.gradle.kts").isFile) {
    apply("$rootDir/dev.gradle.kts")
}