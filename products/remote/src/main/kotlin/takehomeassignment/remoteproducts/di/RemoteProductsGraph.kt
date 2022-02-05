package takehomeassignment.remoteproducts.di

import android.content.res.Resources
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import takehomeassignment.core.Logger
import takehomeassignment.remoteproducts.R
import takehomeassignment.remoteproducts.services.ProductsService

class RemoteProductsGraph(
    appResources: Resources,
    private val logger: Logger
) {
    val productsService: ProductsService = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor(logger::d))
                .build()
        )
        .baseUrl(appResources.getString(R.string.base_url))
        .build()
        .create(ProductsService::class.java)
}
