package takehomeassignment.productdetail.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.merge
import takehomeassignment.localproducts.dao.ProductsDao
import takehomeassignment.productdetail.models.LoadProductEvent
import takehomeassignment.productdetail.models.LoadProductResult
import takehomeassignment.productdetail.models.ProductDetailEffect
import takehomeassignment.productdetail.models.ProductDetailEvent
import takehomeassignment.productdetail.models.ProductDetailResult
import takehomeassignment.productdetail.models.ProductDetailState
import takehomeassignment.utils.mvi.MviViewModel

class ProductDetailViewModel(
    private val dao: ProductsDao,
    id: String,
    initialState: ProductDetailState
) : MviViewModel<ProductDetailEvent, ProductDetailResult, ProductDetailState, ProductDetailEffect>(initialState) {

    init {
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
        return mapLatest { event ->
            val product = dao.queryById(event.id)
            LoadProductResult(product)
        }
    }

    class Factory @Inject constructor(private val dao: ProductsDao) {
        fun create(id: String, initialState: ProductDetailState): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return ProductDetailViewModel(dao, id, initialState) as T
                }
            }
        }
    }
}
