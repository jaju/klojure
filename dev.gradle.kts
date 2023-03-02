dependencies {
    "implementation"("nrepl:nrepl:1.0.0")
}

val sourceSet = project.the<SourceSetContainer>()

sourceSet {
    val main by getting {
        resources {
            srcDirs += srcDir("src/dev/clojure")
        }
    }
}