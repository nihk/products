package takehomeassignment.productdetail.models

sealed class ProductDetailEvent
data class LoadProductEvent(val id: String) : ProductDetailEvent()

sealed class ProductDetailResult
data class LoadProductResult(val product: ProductDetailItem?) : ProductDetailResult()

data class ProductDetailState(
    val product: ProductDetailItem? = null
)
