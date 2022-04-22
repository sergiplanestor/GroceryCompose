import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    appPlugins(GradlePlugin.Kapt, GradlePlugin.Hilt).forEach(::id)
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {

    namespace = "com.splanes.grocery"

    applyAppDefaultConfig()

    defaultConfig {
        manifestPlaceholders["mapsApiKey"] = Properties().apply {
            load(file("${rootDir.absolutePath}/local.properties").inputStream())
        }.getProperty("MAPS_API_MEY")
    }

    applyCompileOptions()

    kotlinOptions { applyKotlinOptions() }

    applyAppFlavors()

    withFeatures(Feature.Compose)

    composeOptions { kotlinCompilerExtensionVersion = DependencyVersion.compose }
}

dependencies {

    module(name = "data")
    module(name = "domain")
    module(name = "ui")

    androidCore()
    androidRuntime()
    hilt()

    compose()
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.android.libraries.places:places:2.6.0")
    test()
}