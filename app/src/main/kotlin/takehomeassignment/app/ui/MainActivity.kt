package takehomeassignment.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.createGraph
import androidx.navigation.fragment.fragment
import dagger.hilt.android.AndroidEntryPoint
import takehomeassignment.app.R
import takehomeassignment.app.di.entryPoint
import takehomeassignment.app.di.main.MainEntryPoint
import takehomeassignment.productdetail.ui.ProductDetailFragment
import takehomeassignment.productlist.ui.ProductListFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.main_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        val entryPoint = entryPoint<MainEntryPoint>()
        supportFragmentManager.fragmentFactory = entryPoint.fragmentFactory
        super.onCreate(savedInstanceState)
        createNavGraph(entryPoint.navController)
    }

    private fun createNavGraph(navController: NavController) {
        navController.apply {
            graph = createGraph(startDestination = ProductListFragment.ROUTE) {
                fragment<ProductListFragment>(route = ProductListFragment.ROUTE)
                fragment<ProductDetailFragment>(route = ProductDetailFragment.ROUTE) {
                    argument(name = ProductDetailFragment.ARG_ID) {
                        type = NavType.StringType
                    }
                }
            }
        }
    }
}
