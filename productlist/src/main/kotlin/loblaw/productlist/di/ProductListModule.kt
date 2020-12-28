package loblaw.productlist.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import loblaw.productlist.repository.DefaultProductListRepository
import loblaw.productlist.repository.ProductListRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductListModule {

    @Binds
    abstract fun productListRepository(productListRepository: DefaultProductListRepository): ProductListRepository
}