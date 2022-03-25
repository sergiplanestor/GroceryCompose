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
    }
}

dependencies {
    implementation("com.android.tools.build:gradle:7.1.2")
    implementation(kotlin("gradle-plugin", version = "1.6.10"))
    implementation("com.google.gms:google-services:4.3.10")
    implementation("com.google.firebase:firebase-crashlytics-gradle:2.8.1")
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.40.5")
}