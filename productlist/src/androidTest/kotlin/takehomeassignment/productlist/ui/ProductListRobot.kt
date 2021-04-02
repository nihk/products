package takehomeassignment.productlist.ui

import android.widget.ImageView
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import javax.inject.Provider
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import org.hamcrest.CoreMatchers.not
import takehomeassignment.productlist.R
import takehomeassignment.productlist.repository.ProductListRepository
import takehomeassignment.productlist.state.ProductsState
import takehomeassignment.productlist.vm.ProductListViewModel
import takehomeassignment.testutils.FakeImageLoader

fun productList(block: ProductListRobot.() -> Unit) {
    val robot = ProductListRobot()
    robot.block()
}

class ProductListRobot {
    private val states = MutableStateFlow<ProductsState?>(null)
    private val onProductClicked = FakeOnProductClicked()

    init {
        launchProductList()
    }

    fun render(state: ProductsState) {
        states.value = state
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
        val matcher = if (isDisplayed) isDisplayed() else not(
            isDisplayed()
        )
        onView(withId(R.id.error_message))
            .check(matches(matcher))
    }

    fun assertProductWasClicked(id: String) {
        assertEquals(id, onProductClicked.clickedId)
    }

    private fun launchProductList() {
        val repository = object : ProductListRepository {
            override fun products(): Flow<ProductsState> = states.filterNotNull()
        }
        val vmFactory = ProductListViewModel.Factory(repository)
        val adapterFactory = Provider { ProductListAdapter(onProductClicked, FakeImageLoader()) }

        launchFragmentInContainer(themeResId = R.style.Theme_MaterialComponents_DayNight_DarkActionBar) {
            ProductListFragment(vmFactory, adapterFactory)
        }
    }
}

private class FakeOnProductClicked : OnProductClicked {
    var clickedId: String? = null

    override fun onProductClicked(id: String, image: ImageView) {
        clickedId = id
    }
}
