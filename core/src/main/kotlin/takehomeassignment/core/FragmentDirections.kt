package takehomeassignment.core

import android.os.Bundle
import androidx.fragment.app.Fragment

data class FragmentDirections(
    val screen: Class<out Fragment>,
    val arguments: Bundle? = null
)
