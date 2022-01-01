package takehomeassignment.app.navigation

import android.view.View
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import javax.inject.Inject
import takehomeassignment.productdetail.ui.ProductDetailFragment
import takehomeassignment.productlist.ui.OnProductClicked

class ToProductDetails @Inject constructor(
    private val navigation: Navigation,
) : OnProductClicked {
    override fun onProductClicked(id: String, view: View) {
        navigation.fragmentManager.commit {
            setReorderingAllowed(true)
            addSharedElement(view, id)
            replace<ProductDetailFragment>(
                containerViewId = navigation.container,
                args = ProductDetailFragment.bundle(id)
            )
            addToBackStack(null)
        }
    }
}
