package takehomeassignment.localproducts.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import takehomeassignment.localproducts.dao.ProductsDao
import takehomeassignment.localproducts.dao.ProductsDaoProvider

@Module
@InstallIn(SingletonComponent::class)
object LocalProductsModule {
    @Provides
    fun productsDao(productsDaoProvider: ProductsDaoProvider): ProductsDao {
        return productsDaoProvider.productsDao()
    }
}