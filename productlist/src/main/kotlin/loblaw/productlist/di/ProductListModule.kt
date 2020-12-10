package loblaw.productlist.di

import androidx.fragment.app.Fragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import loblaw.di.annotations.FragmentKey
import loblaw.localproducts.dao.ProductsDao
import loblaw.localproducts.dao.ProductsDaoProvider
import loblaw.productlist.ui.ProductListFragment

@Module
abstract class ProductListModule {

    @Module
    companion object {
        @Provides
        fun productsDao(productsDaoProvider: ProductsDaoProvider): ProductsDao {
            return productsDaoProvider.productsDao()
        }
    }

    @Binds
    @IntoMap
    @FragmentKey(ProductListFragment::class)
    abstract fun productListFragment(productListFragment: ProductListFragment): Fragment
}