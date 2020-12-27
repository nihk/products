package loblaw.app.di.main

import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import dagger.BindsInstance
import dagger.Subcomponent

@MainScope
@Subcomponent(
    modules = [
        MainModule::class,
        FragmentsModule::class
    ]
)
interface MainComponent {

    val fragmentFactory: FragmentFactory

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance navController: () -> NavController): MainComponent
    }
}