package loblaw.app

import android.app.Application
import loblaw.app.di.AppComponent
import loblaw.app.di.AppComponentProvider
import loblaw.app.di.DaggerAppComponent

@Suppress("unused")
class App : Application(),
    AppComponentProvider {

    override val appComponent: AppComponent by lazy {
        DaggerAppComponent
            .factory()
            .application(this)
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.appInitializers.initialize()
    }
}