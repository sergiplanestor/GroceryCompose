package com.splanes.grocery.app

import android.app.Application
import com.splanes.grocery.BuildConfig
import com.splanes.grocery.utils.logger.tree.LoggerTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class GroceryApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(LoggerTree.init(BuildConfig.DEBUG))
    }
}