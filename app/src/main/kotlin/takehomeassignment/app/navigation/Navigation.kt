package takehomeassignment.app.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager

class Navigation(
    val fragmentManager: FragmentManager,
    @IdRes val container: Int
)
