package takehomeassignment.app.navigation

import androidx.fragment.app.commit
import takehomeassignment.productlist.ui.ProductListDirections

class ToProductList(private val navigation: Navigation) {
    fun go() {
        if (navigation.fragmentManager.findFragmentById(navigation.container) == null) {
            navigation.fragmentManager.commit {
                setReorderingAllowed(true)
                val directions = ProductListDirections()
                replace(
                    navigation.container,
                    directions.screen,
                    directions.arguments
                )
            }
        }
    }
}
