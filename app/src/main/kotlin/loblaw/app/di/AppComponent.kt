package loblaw.app.di

import android.app.Application
import loblaw.app.initializers.AppInitializers
import dagger.BindsInstance
import dagger.Component
import loblaw.app.ui.AppFragmentFactory
import loblaw.productlist.di.ProductListModule

@AppScope
@Component(
    modules = [
        AppModule::class,
        ProductListModule::class
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