plugins {
    libPlugins(
        GradlePlugin.Kapt,
        GradlePlugin.Hilt,
        GradlePlugin.Parcelize
    ).forEach(::id)
}

android {

    namespace = "com.splanes.grocery.data"

    applyLibModuleDefaultConfig()

    applyLibFlavors()

    applyCompileOptions()

    kotlinOptions { applyKotlinOptions() }

}

dependencies {

    module(name = "domain")

    androidCore()
    androidRuntime()

    api(platform("com.google.firebase:firebase-bom:29.2.1"))
    api("com.google.firebase:firebase-auth-ktx")
    api("com.google.firebase:firebase-database-ktx")

    hilt()
    room()

    gson()
}