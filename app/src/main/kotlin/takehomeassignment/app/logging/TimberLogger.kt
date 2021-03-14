package takehomeassignment.app.logging

import javax.inject.Inject
import takehomeassignment.core.Logger
import timber.log.Timber

class TimberLogger @Inject constructor() : Logger {

    override fun d(message: String?) {
        Timber.d(message)
    }

    override fun e(message: String?, throwable: Throwable?) {
        Timber.e(throwable, message)
    }
}
