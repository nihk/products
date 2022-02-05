package takehomeassignment.core

class MulticastLogger(private val loggers: List<Logger>) : Logger {
    override fun d(message: String?) {
        loggers.forEach { logger -> logger.d(message) }
    }

    override fun e(message: String?, throwable: Throwable?) {
        loggers.forEach { logger -> logger.e(message, throwable) }
    }
}
