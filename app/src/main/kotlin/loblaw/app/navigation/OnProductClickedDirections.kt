package loblaw.app.navigation

import androidx.navigation.NavController
import loblaw.productdetail.ui.ProductDetailFragment
import loblaw.productlist.ui.OnProductClicked
import javax.inject.Inject

class OnProductClickedDirections @Inject constructor(
    private val navController: () -> NavController
) : OnProductClicked {

    override fun onProductClicked(id: String) {
        navController().navigate(
            AppNavGraph.Action.productDetail,
            ProductDetailFragment.bundle(id)
        )
    }
}