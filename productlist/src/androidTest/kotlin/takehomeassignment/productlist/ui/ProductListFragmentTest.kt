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
import coil.ImageLoader
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.assertEquals
import org.junit.Test
import takehomeassignment.productlist.R
import takehomeassignment.productlist.repository.ProductListRepository
import takehomeassignment.productlist.state.ProductsState
import takehomeassignment.productlist.vm.ProductListViewModel
import takehomeassignment.testutils.FakeImageLoader

class ProductListFragmentTest {

    @Test
    fun loadingStateShowsLoading() {
        val loading = flowOf(ProductsState.Loading())

        launchProductList(loading)

        onView(withId(R.id.progress_bar))
            .check(matches(isDisplayed()))
    }

    @Test
    fun successStateShowsProducts() {
        val success = flowOf(ProductsState.Success(products))

        launchProductList(success)

        onView(withId(R.id.recycler_view))
            .check(matches(atPosition(0, hasDescendant(withText(products.first().name)))))
    }

    @Test
    fun errorStateWithNoProductsShowsErrorMessage() {
        val error = flowOf(ProductsState.Error(Exception()))

        launchProductList(error)

        onView(withId(R.id.error_message))
            .check(matches(isDisplayed()))
    }

    @Test
    fun errorStateWithProductsDoesNotShowErrorMessage() {
        val error = flowOf(ProductsState.Error(Exception(), products))

        launchProductList(error)

        onView(withId(R.id.error_message))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun clickingProductInvokesCallbackWithCorrectId() {
        val success = flowOf(ProductsState.Success(products))
        val onProductClicked = FakeOnProductClicked()

        launchProductList(success, onProductClicked)

        onView(withId(R.id.recycler_view))
            .perform(actionOnItemAtPosition<ProductViewHolder>(0, click()))

        assertEquals(products.first().id, onProductClicked.clickedId)
    }

    private fun launchProductList(
        products: Flow<ProductsState>,
        onProductClicked: OnProductClicked = FakeOnProductClicked(),
        imageLoader: ImageLoader = FakeImageLoader()
    ) {
        val repository = object : ProductListRepository {
            override fun products(): Flow<ProductsState> = products
        }
        val vmFactory = ProductListViewModel.Factory(repository)

        launchFragmentInContainer(themeResId = R.style.Theme_MaterialComponents_DayNight_DarkActionBar) {
            ProductListFragment(vmFactory, onProductClicked, imageLoader)
        }
    }
}

private class FakeOnProductClicked : OnProductClicked {
    var clickedId: String? = null

    override fun onProductClicked(id: String, image: ImageView) {
        clickedId = id
    }
}
