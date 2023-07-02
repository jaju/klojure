val kotlinVersion = "1.8.22"

repositories {
    gradlePluginPortal()
}

plugins {
    `kotlin-dsl`
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
}
