package takehomeassignment.app.di

import android.app.Application
import android.content.res.Resources
import androidx.room.Room
import androidx.room.RoomDatabase
import coil.ImageLoader
import coil.imageLoader
import takehomeassignment.app.config.AppConfig
import takehomeassignment.app.config.GradleAppConfig
import takehomeassignment.app.data.AppDatabase
import takehomeassignment.app.initializers.AppInitializers
import takehomeassignment.app.initializers.Initializer
import takehomeassignment.app.initializers.StrictModeInitializer
import takehomeassignment.app.initializers.TimberInitializer
import takehomeassignment.app.logging.TimberLogger
import takehomeassignment.asyncutils.CoroutineDispatchers
import takehomeassignment.asyncutils.StandardCoroutineDispatchers
import takehomeassignment.core.Logger
import takehomeassignment.core.MulticastLogger
import takehomeassignment.localproducts.dao.ProductsDao

// Singleton-scoped dependencies
class AppGraph(private val application: Application) {
    private val appConfig: AppConfig = GradleAppConfig()

    private val dispatchers: CoroutineDispatchers = StandardCoroutineDispatchers()

    private val appDatabase: AppDatabase by lazy {
        Room.databaseBuilder(application, AppDatabase::class.java, AppDatabase.name)
            .apply {
                if (appConfig.isDeveloperMode) {
                    setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
                }
            }
            .build()
    }

    val appResources: Resources = application.resources

    val logger: Logger = MulticastLogger(
        loggers = listOf(
            TimberLogger()
        )
    )

    val initializer: Initializer = AppInitializers(
        initializers = listOf(
            TimberInitializer(appConfig),
            StrictModeInitializer(appConfig, logger, dispatchers),
        )
    )

    val productsDao: ProductsDao get() = appDatabase.productsDao()

    val imageLoader: ImageLoader = application.imageLoader

    interface Holder {
        val appGraph: AppGraph
    }
}
