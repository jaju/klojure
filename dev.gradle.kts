dependencies {
    "implementation"("org.slf4j:slf4j-simple:2.0.6")
}

val sourceSet = project.the<SourceSetContainer>()

sourceSet {
    val main by getting {
        resources {
            srcDirs += srcDir("src/dev/clojure")
        }
    }
}