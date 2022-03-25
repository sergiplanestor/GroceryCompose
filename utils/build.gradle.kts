plugins {
    libPlugins(GradlePlugin.Parcelize).forEach(::id)
}

android {

    namespace = "com.splanes.grocery.utils"

    applyLibModuleDefaultConfig()

    applyLibFlavors()

    applyCompileOptions()

    kotlinOptions { applyKotlinOptions() }
}

dependencies {

    androidCore()
    androidRuntime()

    toolkitBaseArch()

    timber(isApi = true)
}