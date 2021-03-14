package takehomeassignment.app.di.main

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import takehomeassignment.productlist.ui.OnProductClicked
import takehomeassignment.app.R
import takehomeassignment.app.navigation.OnProductClickedDirections
import takehomeassignment.app.ui.AppFragmentFactory

@Module
@InstallIn(ActivityComponent::class)
abstract class MainModule {

    companion object {
        @Provides
        fun navController(activity: Activity): NavController {
            val navHostFragment = (activity as AppCompatActivity).supportFragmentManager
                .findFragmentById(R.id.navHostContainer) as NavHostFragment
            return navHostFragment.navController
        }
    }

    @Binds
    abstract fun onProductClicked(onProductClickedDirections: OnProductClickedDirections): OnProductClicked

    @Binds
    abstract fun fragmentFactory(appFragmentFactory: AppFragmentFactory): FragmentFactory
}