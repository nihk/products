package loblaw.app.di.main

import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import dagger.Module
import dagger.Provides
import loblaw.app.navigation.DefaultOnProductClicked
import loblaw.app.ui.AppFragmentFactory
import loblaw.productlist.ui.OnProductClicked

@Module
object MainModule {

    @Provides
    fun onProductClicked(navController: () -> NavController): OnProductClicked {
        return DefaultOnProductClicked(navController)
    }

    @Provides
    fun fragmentFactory(appFragmentFactory: AppFragmentFactory): FragmentFactory = appFragmentFactory
}