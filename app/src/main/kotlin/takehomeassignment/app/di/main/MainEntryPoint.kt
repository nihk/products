package takehomeassignment.app.di.main

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import takehomeassignment.app.navigation.ToProductList

@EntryPoint
@InstallIn(ActivityComponent::class)
interface MainEntryPoint {
    val toProductList: ToProductList
}
