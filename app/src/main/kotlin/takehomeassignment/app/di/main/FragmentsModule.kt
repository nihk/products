package takehomeassignment.app.di.main

import androidx.fragment.app.Fragment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoMap
import takehomeassignment.productdetail.ui.ProductDetailFragment
import takehomeassignment.productlist.ui.ProductListFragment

@Module
@InstallIn(ActivityComponent::class)
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
