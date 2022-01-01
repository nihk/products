package takehomeassignment.app.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton
import takehomeassignment.app.config.AppConfig
import takehomeassignment.app.config.GradleAppConfig
import takehomeassignment.app.data.AppDatabase
import takehomeassignment.app.initializers.AppInitializers
import takehomeassignment.app.initializers.Initializer
import takehomeassignment.app.initializers.InitializerComparator
import takehomeassignment.app.initializers.MainInitializer
import takehomeassignment.app.initializers.StrictModeInitializer
import takehomeassignment.app.initializers.TimberInitializer
import takehomeassignment.app.logging.TimberLogger
import takehomeassignment.asyncutils.CoroutineDispatchers
import takehomeassignment.asyncutils.StandardCoroutineDispatchers
import takehomeassignment.core.Logger
import takehomeassignment.core.MulticastLogger
import takehomeassignment.localproducts.dao.ProductsDaoProvider

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {
    companion object {
        @Provides
        fun appResources(application: Application): Resources = application.resources

        @Singleton
        @Provides
        fun appDatabase(@ApplicationContext appContext: Context, appConfig: AppConfig): AppDatabase {
            return Room.databaseBuilder(appContext, AppDatabase::class.java, AppDatabase.name)
                .apply {
                    if (appConfig.isDeveloperMode) {
                        setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
                    }
                }
                .build()
        }

        @Provides
        fun initializersComparator(): InitializerComparator {
            val priorities = listOf<Class<out Initializer>>(
                TimberInitializer::class.java
            )
            return InitializerComparator(priorities)
        }
    }

    @Binds
    fun gradleAppConfig(gradleAppConfig: GradleAppConfig): AppConfig

    @Binds
    fun multicastLogger(multicastLogger: MulticastLogger): Logger

    @Binds
    @IntoSet
    fun timberLogger(timberLogger: TimberLogger): Logger

    @Binds
    @IntoSet
    fun timberInitializer(timberInitializer: TimberInitializer): Initializer

    @Binds
    @IntoSet
    fun strictModeInitializer(strictModeInitializer: StrictModeInitializer): Initializer

    @Binds
    @IntoSet
    fun mainInitializer(mainInitializer: MainInitializer): Initializer

    @Binds
    fun appInitializers(appInitializers: AppInitializers): Initializer

    @Binds
    fun productsDaoProvider(appDatabase: AppDatabase): ProductsDaoProvider

    @Binds
    fun defaultDispatchers(standardCoroutineDispatchers: StandardCoroutineDispatchers): CoroutineDispatchers
}
