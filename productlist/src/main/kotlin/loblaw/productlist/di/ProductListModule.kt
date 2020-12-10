package loblaw.productlist.di

import androidx.fragment.app.Fragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import loblaw.di.annotations.FragmentKey
import loblaw.productlist.repository.DefaultProductListRepository
import loblaw.productlist.repository.ProductListRepository
import loblaw.productlist.ui.ProductListFragment

@Module
abstract class ProductListModule {

    @Binds
    @IntoMap
    @FragmentKey(ProductListFragment::class)
    abstract fun productListFragment(productListFragment: ProductListFragment): Fragment

    @Binds
    abstract fun productListRepository(productListRepository: DefaultProductListRepository): ProductListRepository
}