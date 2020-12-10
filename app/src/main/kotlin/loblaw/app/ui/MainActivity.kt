package loblaw.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import loblaw.app.R
import loblaw.app.navigation.AppNavGraph
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
                fragment<ProductListFragment>(AppNavGraph.Destination.productList)
            }
        }
    }

    override fun onProductClicked(id: String) {
        // todo
    }
}