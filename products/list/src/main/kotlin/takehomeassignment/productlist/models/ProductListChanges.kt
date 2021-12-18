package takehomeassignment.productlist.models

sealed class ProductListEvent
object FetchProductsEvent : ProductListEvent()
data class ProductClickedEvent(val id: String) : ProductListEvent()

sealed class ProductListResult
object StartLoadingResult : ProductListResult()
data class FetchProductsResult(
    val isCached: Boolean,
    val products: List<ProductListItem>?,
    val error: Throwable?
) : ProductListResult()
data class ProductClickedResult(val id: String) : ProductListResult()

sealed class ProductListEffect
data class ProductClickedEffect(val id: String) : ProductListEffect()

data class ProductListState(
    val isLoading: Boolean = false,
    val products: List<ProductListItem>? = null,
    val error: Throwable? = null
)
