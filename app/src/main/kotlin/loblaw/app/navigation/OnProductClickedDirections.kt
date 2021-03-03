package loblaw.app.navigation

import android.widget.ImageView
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import loblaw.productdetail.ui.ProductDetailFragment
import loblaw.productlist.ui.OnProductClicked
import javax.inject.Inject

class OnProductClickedDirections @Inject constructor(
    private val navController: NavController
) : OnProductClicked {

    override fun onProductClicked(id: String, image: ImageView) {
        navController.navigate(
            AppNavGraph.Destination.productDetail,
            ProductDetailFragment.bundle(id),
            null,
            FragmentNavigatorExtras(image to id)
        )
    }
}