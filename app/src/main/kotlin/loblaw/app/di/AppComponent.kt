package loblaw.app.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import loblaw.app.di.main.MainComponent
import loblaw.app.initializers.AppInitializers
import loblaw.localproducts.di.LocalProductsModule
import loblaw.productlist.di.ProductListModule
import loblaw.remoteproducts.di.RemoteProductsModule
import loblaw.uiutils.di.UiUtilsModule

@AppScope
@Component(
    modules = [
        AppModule::class,
        SubcomponentsModule::class,
        ProductListModule::class,
        RemoteProductsModule::class,
        LocalProductsModule::class,
        UiUtilsModule::class
    ]
)
interface AppComponent {

    val appInitializers: AppInitializers

    @Component.Factory
    interface Factory {
        fun application(@BindsInstance application: Application): AppComponent
    }

    fun mainComponentFactory(): MainComponent.Factory
}