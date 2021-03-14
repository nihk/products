package loblaw.app.initializers

import android.os.StrictMode
import loblaw.app.config.AppConfig
import javax.inject.Inject

class StrictModeInitializer @Inject constructor(private val appConfig: AppConfig) : Initializer {
    override fun initialize() {
        if (!appConfig.isDeveloperMode) {
            return
        }

        StrictMode.ThreadPolicy.Builder()
            .detectDiskReads()
            .detectDiskWrites()
            .detectNetwork()
            .penaltyLog()
            .build()
            .also { policy ->
                StrictMode.setThreadPolicy(policy)
            }

        StrictMode.VmPolicy.Builder()
            .detectLeakedSqlLiteObjects()
            // Disabled until this bug is resolved: https://issuetracker.google.com/issues/167533582
            //.detectLeakedClosableObjects()
            .penaltyLog()
            .build()
            .also { policy ->
                StrictMode.setVmPolicy(policy)
            }
    }
}