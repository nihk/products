package takehomeassignment.productlist.ui

import org.junit.Test
import takehomeassignment.productlist.models.ProductsPacket
import takehomeassignment.utils.products

class ProductListFragmentTest {
    @Test
    fun loadingStateShowsLoading() = productList {
        render(ProductsPacket.Cached(emptyList()))
        assertIsLoading()
    }

    @Test
    fun successStateShowsProducts() = productList {
        render(ProductsPacket.Fresh(products))
        assertProductNameIsDisplayed(position = 0, products.first().name)
    }

    @Test
    fun errorStateWithNoProductsShowsErrorMessage() = productList {
        render(ProductsPacket.Error(Exception(), emptyList()))
        assertErrorMessage(isDisplayed = true)
    }

    @Test
    fun errorStateWithProductsDoesNotShowErrorMessage() = productList {
        render(ProductsPacket.Error(Exception(), products))
        assertErrorMessage(isDisplayed = false)
    }

    @Test
    fun clickingProductInvokesCallbackWithCorrectId() = productList {
        render(ProductsPacket.Fresh(products))
        clickOn(position = 0)
        assertProductWasClicked(id = products.first().id)
    }
}
