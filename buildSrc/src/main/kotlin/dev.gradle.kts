plugins {
    java
    application
}

dependencies {
    implementation("org.slf4j:slf4j-simple:2.0.16")
}

val sourceSet = project.the<SourceSetContainer>()

sourceSet {
    val main by getting {
        java {
            srcDirs += srcDir("src/dev/kotlin")
        }
        resources {
            srcDirs += srcDir("src/dev/clojure")
        }
    }
}

application {
    mainClass.set("klojure.Dev")
}
