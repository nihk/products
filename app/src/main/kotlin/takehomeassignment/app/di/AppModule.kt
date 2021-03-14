package takehomeassignment.app.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import androidx.room.Room
import androidx.room.RoomDatabase.JournalMode
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import takehomeassignment.asyncutils.CoroutineDispatchers
import takehomeassignment.asyncutils.DefaultCoroutineDispatchers
import takehomeassignment.core.Logger
import takehomeassignment.core.MulticastLogger
import takehomeassignment.localproducts.dao.ProductsDaoProvider
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import takehomeassignment.app.config.AppConfig
import takehomeassignment.app.config.GradleAppConfig
import takehomeassignment.app.data.AppDatabase
import takehomeassignment.app.initializers.Initializer
import takehomeassignment.app.initializers.StrictModeInitializer
import takehomeassignment.app.initializers.TimberInitializer
import takehomeassignment.app.logging.TimberLogger
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    companion object {
        @Provides
        fun appResources(application: Application): Resources = application.resources

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

        @Singleton
        @Provides
        fun appDatabase(@ApplicationContext appContext: Context, appConfig: AppConfig): AppDatabase {
            return Room.databaseBuilder(appContext, AppDatabase::class.java, AppDatabase.name)
                .apply {
                    if (appConfig.isDeveloperMode) {
                        setJournalMode(JournalMode.TRUNCATE)
                    }
                }
                .build()
        }
    }

    @Binds
    abstract fun gradleAppConfig(gradleAppConfig: GradleAppConfig): AppConfig

    @Binds
    abstract fun multicastLogger(multicastLogger: MulticastLogger): Logger

    @Binds
    @IntoSet
    abstract fun timberLogger(timberLogger: TimberLogger): Logger

    @Binds
    @IntoSet
    abstract fun timberInitializer(timberInitializer: TimberInitializer): Initializer

    @Binds
    @IntoSet
    abstract fun strictModeInitializer(strictModeInitializer: StrictModeInitializer): Initializer

    @Binds
    abstract fun productsDaoProvider(appDatabase: AppDatabase): ProductsDaoProvider

    @Binds
    abstract fun defaultDispatchers(defaultCoroutineDispatchers: DefaultCoroutineDispatchers): CoroutineDispatchers
}