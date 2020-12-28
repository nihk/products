package loblaw.app

import javax.inject.Inject

interface AppConfig {
    val isDebug: Boolean
}

class BuildConfigAppConfig @Inject constructor() : AppConfig {
    override val isDebug: Boolean get() = BuildConfig.DEBUG
}