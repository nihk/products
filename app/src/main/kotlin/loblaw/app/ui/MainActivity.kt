package loblaw.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavGraphBuilder
import androidx.navigation.createGraph
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import loblaw.app.R
import loblaw.app.di.AppComponentProvider
import loblaw.app.navigation.AppNavGraph
import loblaw.productdetail.ui.ProductDetailFragment
import loblaw.productlist.ui.ProductListFragment
import loblaw.uiutils.defaultAnimations

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = (application as AppComponentProvider).appComponent
            .mainComponentFactory()
            .create { findNavController(R.id.navHostContainer) }
            .fragmentFactory

        super.onCreate(savedInstanceState)
        createNavGraph()
    }

    private fun createNavGraph() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostContainer) as NavHostFragment

        navHostFragment.navController.apply {
            graph = createGraph(
                id = AppNavGraph.id,
                startDestination = AppNavGraph.Destination.productList
            ) {
                productList()
                productDetail()
            }
        }
    }

    private fun NavGraphBuilder.productList() {
        fragment<ProductListFragment>(AppNavGraph.Destination.productList) {
            action(AppNavGraph.Action.productDetail) {
                destinationId = AppNavGraph.Destination.productDetail
                navOptions { defaultAnimations() }
            }
        }
    }

    private fun NavGraphBuilder.productDetail() {
        fragment<ProductDetailFragment>(AppNavGraph.Destination.productDetail)
    }
}