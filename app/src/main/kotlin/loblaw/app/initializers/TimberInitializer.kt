package loblaw.app.initializers

import loblaw.app.config.AppConfig
import timber.log.Timber
import javax.inject.Inject

class TimberInitializer @Inject constructor(
    private val appConfig: AppConfig
) : Initializer {

    override fun initialize() {
        if (!appConfig.isDeveloperMode) {
            return
        }

        Timber.plant(Timber.DebugTree())
    }
}