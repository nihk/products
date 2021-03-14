package takehomeassignment.app.di.main

import androidx.navigation.NavController
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import takehomeassignment.app.ui.MainFragmentFactory

@EntryPoint
@InstallIn(ActivityComponent::class)
interface MainEntryPoint {
    val fragmentFactory: MainFragmentFactory
    val navController: NavController
}
