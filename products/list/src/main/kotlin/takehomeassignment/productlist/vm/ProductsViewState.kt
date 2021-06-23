package takehomeassignment.productlist.vm

import takehomeassignment.localproducts.models.Product

sealed class ProductsViewState {
    abstract val products: List<Product>?

    data class Loading(override val products: List<Product>? = null) : ProductsViewState()
    data class Success(override val products: List<Product>? = null) : ProductsViewState()
    data class Error(val throwable: Throwable, override val products: List<Product>? = null) : ProductsViewState()
}
