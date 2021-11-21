package takehomeassignment.productlist.models

import takehomeassignment.localproducts.models.Product

sealed class ProductsPacket {
    abstract val products: List<Product>?

    data class Cached(override val products: List<Product>) : ProductsPacket()
    data class Fresh(override val products: List<Product>) : ProductsPacket()
    data class Error(
        val throwable: Throwable,
        override val products: List<Product>? = null
    ) : ProductsPacket()
}
