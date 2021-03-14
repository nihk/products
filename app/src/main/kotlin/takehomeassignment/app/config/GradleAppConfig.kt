package takehomeassignment.app.config

import javax.inject.Inject
import takehomeassignment.app.BuildConfig

class GradleAppConfig @Inject constructor() : AppConfig {
    override val isDeveloperMode: Boolean get() = BuildConfig.DEBUG
}
