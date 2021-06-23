package takehomeassignment.productlist.ui

import org.junit.Test
import takehomeassignment.productlist.repository.ProductsResult

class ProductListFragmentTest {
    @Test
    fun loadingStateShowsLoading() = productList {
        render(ProductsResult.Cached(emptyList()))
        assertIsLoading()
    }

    @Test
    fun successStateShowsProducts() = productList {
        render(ProductsResult.Fresh(products))
        assertProductNameIsDisplayed(position = 0, products.first().name)
    }

    @Test
    fun errorStateWithNoProductsShowsErrorMessage() = productList {
        render(ProductsResult.Error(Exception(), emptyList()))
        assertErrorMessage(isDisplayed = true)
    }

    @Test
    fun errorStateWithProductsDoesNotShowErrorMessage() = productList {
        render(ProductsResult.Error(Exception(), products))
        assertErrorMessage(isDisplayed = false)
    }

    @Test
    fun clickingProductInvokesCallbackWithCorrectId() = productList {
        render(ProductsResult.Fresh(products))
        clickOn(position = 0)
        assertProductWasClicked(id = products.first().id)
    }
}
