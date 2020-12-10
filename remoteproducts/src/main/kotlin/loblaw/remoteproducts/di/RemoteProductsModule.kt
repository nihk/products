package loblaw.remoteproducts.di

import android.content.res.Resources
import dagger.Module
import dagger.Provides
import dagger.Reusable
import loblaw.remoteproducts.R
import loblaw.remoteproducts.services.RemoteProductsService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
object RemoteProductsModule {

    @Reusable
    @Provides
    fun remoteProductsService(
        appResources: Resources,
        retrofitBuilder: Retrofit.Builder,
        moshiConverterFactory: MoshiConverterFactory,
        okHttpClient: OkHttpClient
    ): RemoteProductsService {
        return retrofitBuilder
            .addConverterFactory(moshiConverterFactory)
            .client(okHttpClient)
            .baseUrl(appResources.getString(R.string.base_url))
            .build()
            .create(RemoteProductsService::class.java)
    }
}