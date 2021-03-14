package takehomeassignment.app.config

import takehomeassignment.app.BuildConfig
import javax.inject.Inject

class GradleAppConfig @Inject constructor() : AppConfig {
    override val isDeveloperMode: Boolean get() = BuildConfig.DEBUG
}
