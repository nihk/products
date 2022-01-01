package takehomeassignment.app.navigation

import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import javax.inject.Inject
import takehomeassignment.app.di.main.FragmentContainer
import takehomeassignment.productdetail.ui.ProductDetailFragment
import takehomeassignment.productlist.ui.OnProductClicked

class OnProductClickedDirections @Inject constructor(
    private val fragmentManager: FragmentManager,
    @FragmentContainer @IdRes private val container: Int
) : OnProductClicked {

    override fun onProductClicked(id: String, view: View) {
        fragmentManager.commit {
            setReorderingAllowed(true)
            addSharedElement(view, id)
            replace<ProductDetailFragment>(
                containerViewId = container,
                args = ProductDetailFragment.bundle(id)
            )
            addToBackStack(null)
        }
    }
}
