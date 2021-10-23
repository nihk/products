package takehomeassignment.productlist.models

import takehomeassignment.localproducts.models.Product

sealed class ProductsResult {
    abstract val products: List<Product>
    data class Cached(override val products: List<Product>) : ProductsResult()
    data class Fresh(override val products: List<Product>) : ProductsResult()
    data class Error(val throwable: Throwable, override val products: List<Product>) : ProductsResult()
}
