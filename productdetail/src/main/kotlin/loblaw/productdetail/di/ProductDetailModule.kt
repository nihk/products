package loblaw.productdetail.di

import androidx.fragment.app.Fragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import loblaw.di.annotations.FragmentKey
import loblaw.productdetail.ui.ProductDetailFragment

@Module
abstract class ProductDetailModule {

    @Binds
    @IntoMap
    @FragmentKey(ProductDetailFragment::class)
    abstract fun productDetailFragment(productDetailFragment: ProductDetailFragment): Fragment
}