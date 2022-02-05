package takehomeassignment.app

import android.app.Application
import takehomeassignment.app.di.AppGraph

class App : Application(), AppGraph.Holder {
    override val appGraph: AppGraph by lazy { AppGraph(this) }

    override fun onCreate() {
        super.onCreate()
        appGraph.initializer.initialize()
    }
}
