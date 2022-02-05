package takehomeassignment.app.di

import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.FragmentManager
import takehomeassignment.app.R
import takehomeassignment.app.navigation.Navigation
import takehomeassignment.app.navigation.ToProductDetails
import takehomeassignment.app.navigation.ToProductList
import takehomeassignment.app.ui.AppFragmentFactory
import takehomeassignment.productdetail.di.ProductDetailGraph
import takehomeassignment.productlist.di.ProductListGraph

// Activity-scoped dependencies
class MainGraph(
    appGraph: AppGraph,
    fragmentManager: FragmentManager
) {
    private val navigation = Navigation(fragmentManager, R.id.fragment_container)

    private val productDetailGraph = ProductDetailGraph(
        dao = appGraph.productsDao,
        imageLoader = appGraph.imageLoader
    )

    private val productListGraph = ProductListGraph(
        imageLoader = appGraph.imageLoader,
        logger = appGraph.logger,
        dao = appGraph.productsDao,
        onProductClicked = ToProductDetails(navigation),
        appResources = appGraph.appResources
    )

    val fragmentFactory: FragmentFactory = AppFragmentFactory(
        fragments = mapOf(
            productListGraph.productListFragment,
            productDetailGraph.productDetailFragment
        )
    )

    val toProductList = ToProductList(navigation)
}
