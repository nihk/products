package takehomeassignment.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import takehomeassignment.app.initializers.AppInitializers

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var appInitializers: AppInitializers

    override fun onCreate() {
        super.onCreate()
        appInitializers.initialize()
    }
}
