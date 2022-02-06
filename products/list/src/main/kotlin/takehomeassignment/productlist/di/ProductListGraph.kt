package takehomeassignment.productlist.di

import android.content.res.Resources
import androidx.fragment.app.Fragment
import coil.ImageLoader
import takehomeassignment.core.Logger
import takehomeassignment.localproducts.dao.ProductsDao
import takehomeassignment.productlist.repository.DefaultProductListRepository
import takehomeassignment.productlist.ui.OnProductClicked
import takehomeassignment.productlist.ui.ProductListFragment
import takehomeassignment.productlist.vm.ProductListViewModel
import takehomeassignment.remoteproducts.di.RemoteProductsGraph

class ProductListGraph(
    private val imageLoader: ImageLoader,
    private val logger: Logger,
    private val dao: ProductsDao,
    private val onProductClicked: OnProductClicked,
    private val appResources: Resources
) {
    val productListFragment: Pair<Class<out Fragment>, () -> Fragment> get() {
        return ProductListFragment::class.java to {
            ProductListFragment(
                viewModelFactory = { owner ->
                    ProductListViewModel.Factory(
                        repository = DefaultProductListRepository(
                            service = RemoteProductsGraph(appResources, logger).productsService,
                            dao = dao,
                            logger = logger
                        ),
                        logger = logger
                    ).create(owner)
                },
                imageLoader = imageLoader,
                onProductClicked = onProductClicked
            )
        }
    }
}
