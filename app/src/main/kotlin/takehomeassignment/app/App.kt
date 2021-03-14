package takehomeassignment.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import takehomeassignment.app.initializers.AppInitializers
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var appInitializers: AppInitializers

    override fun onCreate() {
        super.onCreate()
        appInitializers.initialize()
    }
}