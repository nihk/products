package takehomeassignment.app.initializers

import android.os.Build
import android.os.StrictMode
import android.os.strictmode.Violation
import javax.inject.Inject
import kotlinx.coroutines.asExecutor
import takehomeassignment.app.config.AppConfig
import takehomeassignment.asyncutils.CoroutineDispatchers
import takehomeassignment.core.Logger

class StrictModeInitializer @Inject constructor(
    private val appConfig: AppConfig,
    private val logger: Logger,
    private val dispatchers: CoroutineDispatchers
) : Initializer {

    override fun initialize() {
        if (!appConfig.isDeveloperMode) {
            return
        }

        val ioExecutor = dispatchers.io.asExecutor()

        StrictMode.ThreadPolicy.Builder()
            .detectDiskReads()
            .detectDiskWrites()
            .detectNetwork()
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    penaltyListener(ioExecutor) { violation: Violation ->
                        logger.e(throwable = violation)
                    }
                } else {
                    penaltyLog()
                }
            }
            .build()
            .also { policy ->
                StrictMode.setThreadPolicy(policy)
            }

        StrictMode.VmPolicy.Builder()
            .detectLeakedSqlLiteObjects()
            // Disabled until this bug is resolved: https://issuetracker.google.com/issues/167533582
//            .detectLeakedClosableObjects()
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    penaltyListener(ioExecutor) { violation: Violation ->
                        logger.e(throwable = violation)
                    }
                } else {
                    penaltyLog()
                }
            }
            .build()
            .also { policy ->
                StrictMode.setVmPolicy(policy)
            }
    }
}
