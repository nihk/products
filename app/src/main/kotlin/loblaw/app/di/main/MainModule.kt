package loblaw.app.di.main

import androidx.fragment.app.FragmentFactory
import dagger.Binds
import dagger.Module
import loblaw.app.navigation.OnProductClickedDirections
import loblaw.app.ui.AppFragmentFactory
import loblaw.productlist.ui.OnProductClicked

@Module
abstract class MainModule {
    @Binds
    abstract fun onProductClicked(onProductClickedDirections: OnProductClickedDirections): OnProductClicked

    @Binds
    abstract fun fragmentFactory(appFragmentFactory: AppFragmentFactory): FragmentFactory
}