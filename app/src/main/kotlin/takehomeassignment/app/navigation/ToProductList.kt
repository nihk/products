package takehomeassignment.app.navigation

import androidx.fragment.app.commit
import androidx.fragment.app.replace
import javax.inject.Inject
import takehomeassignment.productlist.ui.ProductListFragment

class ToProductList @Inject constructor(private val navigation: Navigation) {
    fun navigate() {
        if (navigation.fragmentManager.findFragmentById(navigation.container) == null) {
            navigation.fragmentManager.commit {
                setReorderingAllowed(true)
                replace<ProductListFragment>(containerViewId = navigation.container)
            }
        }
    }
}
