package loblaw.uiutils.di

import android.content.Context
import coil.ImageLoader
import coil.imageLoader
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object UiUtilsModule {
    @Reusable
    @Provides
    fun defaultImageLoader(appContext: Context): ImageLoader = appContext.imageLoader
}