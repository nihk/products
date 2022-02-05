package takehomeassignment.productdetail.di

import androidx.fragment.app.Fragment
import coil.ImageLoader
import takehomeassignment.localproducts.dao.ProductsDao
import takehomeassignment.productdetail.ui.ProductDetailFragment
import takehomeassignment.productdetail.vm.ProductDetailViewModel

class ProductDetailGraph(
    private val dao: ProductsDao,
    private val imageLoader: ImageLoader
) {
    val productDetailFragment: Pair<Class<out Fragment>, () -> Fragment> get() {
        return ProductDetailFragment::class.java to {
            ProductDetailFragment(
                viewModelFactory = { owner, id ->
                    ProductDetailViewModel.Factory(
                        dao = dao,
                        id = id
                    ).create(owner)
                },
                imageLoader = imageLoader
            )
        }
    }
}
