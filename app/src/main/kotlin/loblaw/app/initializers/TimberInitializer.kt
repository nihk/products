package loblaw.app.initializers

import loblaw.app.AppConfig
import timber.log.Timber
import javax.inject.Inject

class TimberInitializer @Inject constructor(
    private val appConfig: AppConfig
) : Initializer {

    override fun initialize() {
        if (appConfig.isDebug) {
            Timber.plant(Timber.DebugTree())
        }
    }
}