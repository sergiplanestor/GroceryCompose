plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
}

gradlePlugin {
    plugins {
        register("common-config") {
            id = "common-config"
            implementationClass = "com.splanes.plugins.CommonConfigPlugin"
        }
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

dependencies {
    implementation("com.android.tools.build:gradle:7.1.3")
    implementation(kotlin("gradle-plugin", version = "1.6.10"))
    implementation("com.google.gms:google-services:4.3.10")
    implementation("com.google.firebase:firebase-crashlytics-gradle:2.8.1")
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.40.5")
    implementation("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")
}