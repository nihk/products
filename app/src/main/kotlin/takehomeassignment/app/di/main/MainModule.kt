package takehomeassignment.app.di.main

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.FragmentManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import takehomeassignment.app.R
import takehomeassignment.app.navigation.ToProductDetails
import takehomeassignment.app.ui.MainFragmentFactory
import takehomeassignment.productlist.ui.OnProductClicked

@Module
@InstallIn(ActivityComponent::class)
interface MainModule {

    companion object {
        @Provides
        fun fragmentManager(activity: Activity): FragmentManager = (activity as FragmentActivity).supportFragmentManager

        @Provides
        @FragmentContainer
        fun fragmentContainer() = R.id.fragment_container
    }

    @Binds
    fun onProductClicked(toProductDetails: ToProductDetails): OnProductClicked

    @Binds
    fun fragmentFactory(fragmentFactory: MainFragmentFactory): FragmentFactory
}
