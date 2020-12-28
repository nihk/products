package loblaw.localproducts.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import loblaw.localproducts.dao.ProductsDao
import loblaw.localproducts.dao.ProductsDaoProvider

@Module
@InstallIn(SingletonComponent::class)
object LocalProductsModule {
    @Provides
    fun productsDao(productsDaoProvider: ProductsDaoProvider): ProductsDao {
        return productsDaoProvider.productsDao()
    }
}