plugins {
    libPlugins(
        GradlePlugin.Parcelize,
        GradlePlugin.Kapt,
        GradlePlugin.Hilt
    ).forEach(::id)
}

android {

    namespace = "com.splanes.grocery.domain"

    applyLibModuleDefaultConfig()

    applyLibFlavors()

    applyCompileOptions()

    kotlinOptions { applyKotlinOptions() }
}

dependencies {

    module(name = "utils", isApi = true)

    androidCore()
    androidRuntime()

    hilt()
    toolkitBaseArch()

    timber(isApi = true)
}