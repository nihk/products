package loblaw.remoteproducts.di

import android.content.res.Resources
import dagger.Module
import dagger.Provides
import dagger.Reusable
import loblaw.remoteproducts.R
import loblaw.remoteproducts.services.ProductsService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
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