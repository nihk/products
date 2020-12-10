package loblaw.app.ui

import android.content.Context
import androidx.navigation.fragment.NavHostFragment
import loblaw.app.di.AppComponentProvider
import loblaw.uiutils.activityApplication

class MainNavHostFragment : NavHostFragment() {

    override fun onAttach(context: Context) {
        childFragmentManager.fragmentFactory =
            (activityApplication as AppComponentProvider).appComponent.appFragmentFactory
        super.onAttach(context)
    }
}