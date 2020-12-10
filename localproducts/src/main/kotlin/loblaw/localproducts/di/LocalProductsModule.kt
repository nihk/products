package loblaw.localproducts.di

import dagger.Module
import dagger.Provides
import loblaw.localproducts.dao.ProductsDao
import loblaw.localproducts.dao.ProductsDaoProvider

@Module
object LocalProductsModule {
    @Provides
    fun productsDao(productsDaoProvider: ProductsDaoProvider): ProductsDao {
        return productsDaoProvider.productsDao()
    }
}