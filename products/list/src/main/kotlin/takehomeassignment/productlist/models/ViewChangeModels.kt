package takehomeassignment.productlist.models

import takehomeassignment.localproducts.models.Product

sealed class ViewEvent
object FetchProductsEvent : ViewEvent()
data class ProductClickedEvent(val id: String) : ViewEvent()

sealed class ViewResult
data class FetchProductsResult(
    val isLoading: Boolean,
    val products: List<Product>?,
    val error: Throwable?
) : ViewResult()
data class ProductClickedResult(val id: String) : ViewResult()

sealed class ViewEffect
data class ProductClickedEffect(val id: String) : ViewEffect()

data class ViewState(
    val isLoading: Boolean = false,
    val products: List<Product>? = null,
    val error: Throwable? = null
)
