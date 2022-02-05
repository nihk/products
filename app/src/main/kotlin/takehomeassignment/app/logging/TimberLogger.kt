package takehomeassignment.app.logging

import takehomeassignment.core.Logger
import timber.log.Timber

class TimberLogger : Logger {

    override fun d(message: String?) {
        Timber.d(message)
    }

    override fun e(message: String?, throwable: Throwable?) {
        Timber.e(throwable, message)
    }
}
