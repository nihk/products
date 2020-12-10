package loblaw.app.di

import android.app.Application
import loblaw.app.initializers.AppInitializers
import dagger.BindsInstance
import dagger.Component
import loblaw.app.ui.AppFragmentFactory
import loblaw.localproducts.di.LocalProductsModule
import loblaw.productlist.di.ProductListModule
import loblaw.remoteproducts.di.RemoteProductsModule

@AppScope
@Component(
    modules = [
        AppModule::class,
        ProductListModule::class,
        RemoteProductsModule::class,
        LocalProductsModule::class
    ]
)
interface AppComponent {

    val appInitializers: AppInitializers
    val appFragmentFactory: AppFragmentFactory

    @Component.Factory
    interface Factory {
        fun application(@BindsInstance application: Application): AppComponent
    }
}