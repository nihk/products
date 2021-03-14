package loblaw.app.config

import loblaw.app.BuildConfig
import javax.inject.Inject

class GradleAppConfig @Inject constructor() : AppConfig {
    override val isDeveloperMode: Boolean get() = BuildConfig.DEBUG
}
