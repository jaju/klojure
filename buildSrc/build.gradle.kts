val kotlinVersion = "1.9.0"

repositories {
    gradlePluginPortal()
}

plugins {
    `kotlin-dsl`
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
}
