package takehomeassignment.productlist.models

import takehomeassignment.localproducts.models.Product

sealed interface ProductsResult {
    val products: List<Product>?

    sealed class Transient : ProductsResult

    object Started : Transient() { override val products: List<Product>? = null }
    data class Cached(override val products: List<Product>) : Transient()
    data class Fresh(override val products: List<Product>) : ProductsResult
    data class Error(val throwable: Throwable, override val products: List<Product>) : ProductsResult
}
