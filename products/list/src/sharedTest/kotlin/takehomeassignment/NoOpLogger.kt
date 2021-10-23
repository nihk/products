package takehomeassignment

import takehomeassignment.core.Logger

class NoOpLogger : Logger {
    override fun d(message: String?) = Unit
    override fun e(message: String?, throwable: Throwable?) = Unit
}
