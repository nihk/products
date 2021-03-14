package takehomeassignment.core

import javax.inject.Inject

class MulticastLogger @Inject constructor(
    private val loggers: Set<@JvmSuppressWildcards Logger>
) : Logger {

    override fun d(message: String?) {
        loggers.forEach { logger -> logger.d(message) }
    }

    override fun e(message: String?, throwable: Throwable?) {
        loggers.forEach { logger -> logger.e(message, throwable) }
    }
}
