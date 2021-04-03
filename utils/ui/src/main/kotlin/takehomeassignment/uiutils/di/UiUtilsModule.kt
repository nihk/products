package takehomeassignment.uiutils.di

import android.content.Context
import coil.ImageLoader
import coil.imageLoader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UiUtilsModule {
    @Provides
    fun defaultImageLoader(@ApplicationContext appContext: Context): ImageLoader = appContext.imageLoader
}
