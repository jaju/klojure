repositories {
    gradlePluginPortal()
}

val kotlinVersion = "1.8.10"

plugins {
    `kotlin-dsl`
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
}
