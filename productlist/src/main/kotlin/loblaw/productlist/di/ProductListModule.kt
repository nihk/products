package loblaw.productlist.di

import dagger.Binds
import dagger.Module
import loblaw.productlist.repository.DefaultProductListRepository
import loblaw.productlist.repository.ProductListRepository

@Module
abstract class ProductListModule {

    @Binds
    abstract fun productListRepository(productListRepository: DefaultProductListRepository): ProductListRepository
}