package takehomeassignment.remoteproducts.di

import android.content.res.Resources
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import takehomeassignment.remoteproducts.R
import takehomeassignment.remoteproducts.services.ProductsService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RemoteProductsModule {

    @Reusable
    @Provides
    fun productsService(
        appResources: Resources,
        retrofitBuilder: Retrofit.Builder,
        moshiConverterFactory: MoshiConverterFactory,
        okHttpClient: OkHttpClient
    ): ProductsService {
        return retrofitBuilder
            .addConverterFactory(moshiConverterFactory)
            .client(okHttpClient)
            .baseUrl(appResources.getString(R.string.base_url))
            .build()
            .create(ProductsService::class.java)
    }
}