package takehomeassignment.app.navigation

import android.widget.ImageView
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import javax.inject.Inject
import takehomeassignment.productdetail.ui.ProductDetailFragment
import takehomeassignment.productlist.ui.OnProductClicked

class OnProductClickedDirections @Inject constructor(
    private val navController: NavController
) : OnProductClicked {

    override fun onProductClicked(id: String, image: ImageView) {
        navController.navigate(
            route = ProductDetailFragment.route(id),
            navigatorExtras = FragmentNavigatorExtras(image to id)
        )
    }
}
