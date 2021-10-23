package takehomeassignment.productlist.models

import takehomeassignment.localproducts.models.Product

sealed class ViewEvent
object FetchProductsEvent : ViewEvent()

sealed class ViewResult
data class FetchProductsResult(
    val isCached: Boolean,
    val products: List<Product>,
    val error: Throwable?
) : ViewResult()

data class ViewState(
    val isLoading: Boolean = false,
    val products: List<Product>? = null,
    val error: Throwable? = null
)
