package takehomeassignment.app.config

import takehomeassignment.app.BuildConfig

class GradleAppConfig : AppConfig {
    override val isDeveloperMode: Boolean get() = BuildConfig.DEBUG
}
