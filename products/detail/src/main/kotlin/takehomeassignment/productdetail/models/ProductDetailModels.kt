package takehomeassignment.productdetail.models

import takehomeassignment.localproducts.models.Product

sealed class ProductDetailEvent
data class LoadProductEvent(val id: String) : ProductDetailEvent()

sealed class ProductDetailResult
data class LoadProductResult(val product: Product?) : ProductDetailResult()

data class ProductDetailState(
    val product: Product? = null
)
