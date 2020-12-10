package loblaw.app

import loblaw.app.di.AppScope
import javax.inject.Inject

interface AppConfig {
    val isDebug: Boolean
}

@AppScope
class BuildConfigAppConfig @Inject constructor() : AppConfig {
    override val isDebug: Boolean get() = BuildConfig.DEBUG
}