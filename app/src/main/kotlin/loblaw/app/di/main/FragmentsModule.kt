package loblaw.app.di.main

import androidx.fragment.app.Fragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import loblaw.productdetail.ui.ProductDetailFragment
import loblaw.productlist.ui.ProductListFragment

@Module
abstract class FragmentsModule {
    @Binds
    @IntoMap
    @FragmentKey(ProductListFragment::class)
    abstract fun productListFragment(productListFragment: ProductListFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(ProductDetailFragment::class)
    abstract fun productDetailFragment(productDetailFragment: ProductDetailFragment): Fragment
}