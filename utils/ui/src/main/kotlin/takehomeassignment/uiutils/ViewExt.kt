package takehomeassignment.uiutils

import android.view.View
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun View.clicks(): Flow<Unit> = callbackFlow {
    val listener = View.OnClickListener { trySend(Unit) }
    setOnClickListener(listener)
    awaitClose { setOnClickListener(null) }
}
