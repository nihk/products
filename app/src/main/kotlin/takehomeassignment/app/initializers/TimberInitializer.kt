package takehomeassignment.app.initializers

import javax.inject.Inject
import takehomeassignment.app.config.AppConfig
import timber.log.Timber

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
