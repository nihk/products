package loblaw.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.createGraph
import androidx.navigation.fragment.fragment
import dagger.hilt.android.AndroidEntryPoint
import loblaw.app.R
import loblaw.app.di.entryPoint
import loblaw.app.di.main.MainEntryPoint
import loblaw.app.navigation.AppNavGraph
import loblaw.productdetail.ui.ProductDetailFragment
import loblaw.productlist.ui.ProductListFragment

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
            graph = createGraph(
                id = AppNavGraph.id,
                startDestination = AppNavGraph.Destination.productList
            ) {
                fragment<ProductListFragment>(AppNavGraph.Destination.productList)
                fragment<ProductDetailFragment>(AppNavGraph.Destination.productDetail)
            }
        }
    }
}