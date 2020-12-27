package loblaw.productlist.ui

import androidx.fragment.app.testing.launchFragment
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import loblaw.productlist.R
import loblaw.productlist.repository.ProductListRepository
import loblaw.productlist.state.ProductsState
import loblaw.productlist.vm.ProductListViewModel
import org.hamcrest.CoreMatchers.not
import org.junit.Test

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
            .check(matches(atPosition(0, hasDescendant(withText("RAPID CLEAR® Spot Gel")))))
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

    private fun launchProductList(
        products: Flow<ProductsState>
    ) {
        val repository = object : ProductListRepository {
            override fun products(): Flow<ProductsState> = products
        }
        val vmFactory = ProductListViewModel.Factory(repository)
        val onProductClicked = FakeOnProductClicked()

        launchFragment(themeResId = R.style.Theme_MaterialComponents_DayNight_DarkActionBar) {
            ProductListFragment(vmFactory, onProductClicked)
        }
    }
}

private class FakeOnProductClicked : OnProductClicked {
    var clickedId: String? = null

    override fun onProductClicked(id: String) {
        clickedId = id
    }
}