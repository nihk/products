package takehomeassignment.productdetail.vm

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import takehomeassignment.localproducts.dao.ProductsDao
import takehomeassignment.productdetail.models.LoadProductEvent
import takehomeassignment.productdetail.models.LoadProductResult
import takehomeassignment.productdetail.models.ProductDetailEvent
import takehomeassignment.productdetail.models.ProductDetailResult
import takehomeassignment.productdetail.models.ProductDetailState
import takehomeassignment.utils.mvi.MviViewModel

internal class ProductDetailViewModel(
    private val dao: ProductsDao,
    private val id: String
) : MviViewModel<ProductDetailEvent, ProductDetailResult, ProductDetailState, Nothing>(
    ProductDetailState()
) {

    override fun onStart() {
        processEvent(LoadProductEvent(id))
    }

    override fun Flow<ProductDetailEvent>.toResults(): Flow<ProductDetailResult> {
        return merge(
            filterIsInstance<LoadProductEvent>().toLoadProductResults()
        )
    }

    override fun ProductDetailResult.reduce(state: ProductDetailState): ProductDetailState {
        return when (this) {
            is LoadProductResult -> state.copy(
                product = product
            )
            else -> state
        }
    }

    private fun Flow<LoadProductEvent>.toLoadProductResults(): Flow<ProductDetailResult> {
        return flatMapLatest { event -> dao.queryById(event.id) }
            .map { product -> LoadProductResult(product.toProductDetailItem()) }
    }

    class Factory(
        private val dao: ProductsDao,
        private val id: String
    ) {
        fun create(owner: SavedStateRegistryOwner): ViewModelProvider.Factory {
            return object : AbstractSavedStateViewModelFactory(owner, null) {
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    @Suppress("UNCHECKED_CAST")
                    return ProductDetailViewModel(dao, id) as T
                }
            }
        }
    }
}
