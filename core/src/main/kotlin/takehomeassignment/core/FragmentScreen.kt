package takehomeassignment.core

import androidx.fragment.app.Fragment

interface FragmentScreen {
    val screen: Pair<Class<out Fragment>, () -> Fragment>
}
