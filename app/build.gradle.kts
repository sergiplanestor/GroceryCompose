plugins {
    appPlugins(GradlePlugin.Kapt, GradlePlugin.Hilt).forEach(::id)
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {

    namespace = "com.splanes.grocery"

    applyAppDefaultConfig()

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
    test()
}