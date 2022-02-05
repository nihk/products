package takehomeassignment.app.navigation

import android.view.View
import androidx.fragment.app.commit
import takehomeassignment.productdetail.ui.ProductDetailDirections
import takehomeassignment.productlist.ui.OnProductClicked

class ToProductDetails(
    private val navigation: Navigation,
) : OnProductClicked {
    override fun onProductClicked(id: String, view: View) {
        navigation.fragmentManager.commit {
            setReorderingAllowed(true)
            addSharedElement(view, id)
            val directions = ProductDetailDirections(id)
            replace(
                navigation.container,
                directions.screen,
                directions.arguments
            )
            addToBackStack(null)
        }
    }
}
