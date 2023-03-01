dependencies {
    "implementation"("nrepl:nrepl:1.0.0")
}

val sourceSet = project.the<SourceSetContainer>()
sourceSet["main"].resources.srcDirs("src/dev/clojure")