package loblaw.app.di.main

import android.app.Activity
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.findNavController
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import loblaw.app.R
import loblaw.app.navigation.OnProductClickedDirections
import loblaw.app.ui.AppFragmentFactory
import loblaw.productlist.ui.OnProductClicked

@Module
@InstallIn(ActivityComponent::class)
abstract class MainModule {

    companion object {
        @Provides
        fun navController(activity: Activity): @JvmSuppressWildcards () -> NavController {
            return { activity.findNavController(R.id.navHostContainer) }
        }
    }

    @Binds
    abstract fun onProductClicked(onProductClickedDirections: OnProductClickedDirections): OnProductClicked

    @Binds
    abstract fun fragmentFactory(appFragmentFactory: AppFragmentFactory): FragmentFactory
}