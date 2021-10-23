package takehomeassignment.productlist.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import takehomeassignment.productlist.repository.DefaultProductListRepository
import takehomeassignment.productlist.repository.ProductListRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductListModule {
    @Binds
    abstract fun productListRepository(productListRepository: DefaultProductListRepository): ProductListRepository
}
