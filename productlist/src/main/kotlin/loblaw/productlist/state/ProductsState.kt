package loblaw.productlist.state

import loblaw.localproducts.models.Product

sealed class ProductsState {
    abstract val products: List<Product>?

    data class Loading(override val products: List<Product>? = null): ProductsState()
    data class Success(override val products: List<Product>? = null): ProductsState()
    data class Error(val throwable: Throwable, override val products: List<Product>? = null): ProductsState()
}