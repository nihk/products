package takehomeassignment.app.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import javax.inject.Inject
import takehomeassignment.app.di.main.FragmentContainer

class Navigation @Inject constructor(
    val fragmentManager: FragmentManager,
    @FragmentContainer @IdRes val container: Int
)
