package com.cmk.poke.initializer

import android.content.Context
import androidx.startup.Initializer
import com.cmk.poke.BuildConfig
import timber.log.Timber

/**
 *  App Startup is a Jetpack library that provides a way to initialize components at app startup using a single ContentProvider.
 *  App Startup automatically initializes components (and their dependencies) declared in the manifest file under the InitializationProvider entry.
 *  App Startup allows controlling the order in which components are initialized.
 *  App Startup provides a way to lazily initialize components.
 *
 *  Timber是一个轻量级的第三方库，能够帮助开发者更好的使用Android Log
 */
class TimberInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.d("TimberInitializer is initialized.")
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}