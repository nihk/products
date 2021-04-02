package takehomeassignment.productlist.ui

import org.junit.Test
import takehomeassignment.productlist.state.ProductsState

class ProductListFragmentTest {
    @Test
    fun loadingStateShowsLoading() {
        productList {
            render(ProductsState.Loading())
            assertIsLoading()
        }
    }

    @Test
    fun successStateShowsProducts() {
        productList {
            render(ProductsState.Success(products))
            assertProductNameIsDisplayed(0, products.first().name)
        }
    }

    @Test
    fun errorStateWithNoProductsShowsErrorMessage() {
        productList {
            render(ProductsState.Error(Exception()))
            assertErrorMessage(isDisplayed = true)
        }
    }

    @Test
    fun errorStateWithProductsDoesNotShowErrorMessage() {
        productList {
            render(ProductsState.Error(Exception(), products))
            assertErrorMessage(isDisplayed = false)
        }
    }

    @Test
    fun clickingProductInvokesCallbackWithCorrectId() {
        productList {
            render(ProductsState.Success(products))
            clickOn(position = 0)
            assertProductWasClicked(id = products.first().id)
        }
    }
}
