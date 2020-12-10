package loblaw.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.createGraph
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import loblaw.app.R
import loblaw.app.navigation.AppNavGraph
import loblaw.productdetail.ui.ProductDetailFragment
import loblaw.productlist.ui.OnProductClicked
import loblaw.productlist.ui.ProductListFragment

class MainActivity : AppCompatActivity(R.layout.activity_main), OnProductClicked {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNavGraph()
    }

    private fun createNavGraph() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.mainNavHostContainer) as NavHostFragment

        navHostFragment.navController.apply {
            graph = createGraph(
                id = AppNavGraph.id,
                startDestination = AppNavGraph.Destination.productList
            ) {
                fragment<ProductListFragment>(AppNavGraph.Destination.productList) {
                    action(AppNavGraph.Action.productDetail) {
                        destinationId = AppNavGraph.Destination.productDetail
                        navOptions {
                            anim {
                                enter = R.animator.nav_default_enter_anim
                                exit = R.animator.nav_default_exit_anim
                                popEnter = R.animator.nav_default_pop_enter_anim
                                popExit = R.animator.nav_default_pop_exit_anim
                            }
                        }
                    }
                }
                fragment<ProductDetailFragment>(AppNavGraph.Destination.productDetail)
            }
        }
    }

    override fun onProductClicked(id: String) {
        findNavController(R.id.mainNavHostContainer).navigate(
            AppNavGraph.Action.productDetail,
            ProductDetailFragment.bundle(id)
        )
    }
}