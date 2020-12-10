package loblaw.app.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import androidx.fragment.app.FragmentFactory
import androidx.room.Room
import androidx.room.RoomDatabase.JournalMode
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.multibindings.IntoSet
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import loblaw.app.AppConfig
import loblaw.app.BuildConfigAppConfig
import loblaw.app.data.AppDatabase
import loblaw.app.initializers.Initializer
import loblaw.app.initializers.TimberInitializer
import loblaw.app.logging.TimberLogger
import loblaw.app.ui.AppFragmentFactory
import loblaw.core.Logger
import loblaw.core.MulticastLogger
import loblaw.di.annotations.AppContext

@Module
abstract class AppModule {

    @Module
    companion object {
        @Provides
        @AppContext
        fun appContext(application: Application): Context = application.applicationContext

        @Provides
        fun appResources(application: Application): Resources = application.resources

        @Provides
        fun retrofitBuilder() = Retrofit.Builder()

        @Provides
        fun moshiConverterFactory(): MoshiConverterFactory = MoshiConverterFactory.create()

        @Provides
        fun okHttpClientBuilder(): OkHttpClient.Builder {
            return OkHttpClient.Builder()
        }

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

        @AppScope
        @Provides
        fun appDatabase(@AppContext appContext: Context, appConfig: AppConfig): AppDatabase {
            return Room.databaseBuilder(appContext, AppDatabase::class.java, AppDatabase.name)
                .apply {
                    if (appConfig.isDebug) {
                        setJournalMode(JournalMode.TRUNCATE)
                    }
                }
                .build()
        }
    }

    @Binds
    abstract fun fragmentFactory(appFragmentFactory: AppFragmentFactory): FragmentFactory

    @Binds
    abstract fun buildConfigAppConfig(buildConfigAppConfig: BuildConfigAppConfig): AppConfig

    @Binds
    abstract fun multicastLogger(multicastLogger: MulticastLogger): Logger

    @Binds
    @IntoSet
    abstract fun timberLogger(timberLogger: TimberLogger): Logger

    @Binds
    @IntoSet
    abstract fun timberInitializer(timberInitializer: TimberInitializer): Initializer
}