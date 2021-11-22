package takehomeassignment.productlist.ui

import android.view.View
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.savedstate.SavedStateRegistryOwner
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.assertEquals
import takehomeassignment.NoOpLogger
import takehomeassignment.productlist.R
import takehomeassignment.productlist.models.ProductListEvent
import takehomeassignment.productlist.models.ProductsPacket
import takehomeassignment.productlist.models.ProductListState
import takehomeassignment.productlist.repository.ProductListRepository
import takehomeassignment.productlist.vm.ProductListViewModel
import takehomeassignment.testutils.FakeImageLoader

fun productList(block: ProductListRobot.() -> Unit) {
    val robot = ProductListRobot()
    robot.block()
}

class ProductListRobot {
    private val packets = MutableStateFlow<ProductsPacket?>(null)
    private val onProductClicked = FakeOnProductClicked()

    init {
        launchProductList()
    }

    fun render(packet: ProductsPacket) {
        packets.value = packet
    }

    fun clickOn(position: Int) {
        onView(withId(R.id.recycler_view))
            .perform(actionOnItemAtPosition<ProductViewHolder>(position, click()))
    }

    fun assertIsLoading() {
        onView(withId(R.id.progress_bar))
            .check(matches(isDisplayed()))
    }

    fun assertProductNameIsDisplayed(position: Int, name: String) {
        onView(withId(R.id.recycler_view))
            .check(matches(atPosition(position, hasDescendant(withText(name)))))
    }

    fun assertErrorMessage(isDisplayed: Boolean) {
        val matcher = if (isDisplayed) isDisplayed() else not(isDisplayed())
        onView(withId(R.id.error_message))
            .check(matches(matcher))
    }

    fun assertProductWasClicked(id: String) {
        assertEquals(id, onProductClicked.clickedId)
    }

    private fun launchProductList() {
        val repository = object : ProductListRepository {
            override fun products(): Flow<ProductsPacket> = packets.filterNotNull()
        }
        val vmFactory = object : ProductListViewModel.Factory.Factory {
            override fun create(
                owner: SavedStateRegistryOwner,
                initialState: ProductListState,
                initialEvents: List<ProductListEvent>
            ): ProductListViewModel.Factory {
                return ProductListViewModel.Factory(repository, NoOpLogger(), owner, initialState, initialEvents)
            }
        }

        launchFragmentInContainer(themeResId = R.style.Theme_MaterialComponents_DayNight_DarkActionBar) {
            ProductListFragment(vmFactory, onProductClicked, FakeImageLoader())
        }
    }
}

private class FakeOnProductClicked : OnProductClicked {
    var clickedId: String? = null

    override fun onProductClicked(id: String, view: View) {
        clickedId = id
    }
}
