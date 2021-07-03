package takehomeassignment.remoteproducts.di

import android.content.res.Resources
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import takehomeassignment.core.Logger
import takehomeassignment.remoteproducts.R
import takehomeassignment.remoteproducts.services.ProductsService

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

    @Provides
    fun retrofitBuilder(): Retrofit.Builder = Retrofit.Builder()

    @Provides
    fun moshiConverterFactory(): MoshiConverterFactory = MoshiConverterFactory.create()

    @Provides
    fun okHttpClientBuilder(): OkHttpClient.Builder = OkHttpClient.Builder()

    @Provides
    fun httpLogger(logger: Logger): HttpLoggingInterceptor.Logger {
        return HttpLoggingInterceptor.Logger { message -> logger.d(message) }
    }

    @Provides
    fun httpLoggingInterceptor(httpLogger: HttpLoggingInterceptor.Logger): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(httpLogger)
            .also { it.level = HttpLoggingInterceptor.Level.BASIC }
    }

    @Reusable
    @Provides
    fun okHttpClient(
        builder: OkHttpClient.Builder,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return builder
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }
}
