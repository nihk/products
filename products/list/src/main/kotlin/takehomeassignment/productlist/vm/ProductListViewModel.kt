package takehomeassignment.productlist.vm

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import takehomeassignment.core.Logger
import takehomeassignment.productlist.models.FetchProductsEvent
import takehomeassignment.productlist.models.FetchProductsResult
import takehomeassignment.productlist.models.ProductClickedEffect
import takehomeassignment.productlist.models.ProductClickedEvent
import takehomeassignment.productlist.models.ProductClickedResult
import takehomeassignment.productlist.models.ProductsResult
import takehomeassignment.productlist.models.ProductListEffect
import takehomeassignment.productlist.models.ProductListEvent
import takehomeassignment.productlist.models.ProductListResult
import takehomeassignment.productlist.models.ProductListState
import takehomeassignment.productlist.repository.ProductListRepository
import takehomeassignment.utils.mvi.MviViewModel

class ProductListViewModel(
    private val repository: ProductListRepository,
    private val logger: Logger,
    private val handle: SavedStateHandle,
    initialState: ProductListState
) : MviViewModel<ProductListEvent, ProductListResult, ProductListState, ProductListEffect>(initialState) {

    init {
        processEvent(FetchProductsEvent)
    }

    override fun Flow<ProductListEvent>.toResults(): Flow<ProductListResult> {
        return merge(
            filterIsInstance<FetchProductsEvent>().toFetchProductsResults(),
            filterIsInstance<ProductClickedEvent>().toProductClickedResults()
        )
    }

    override fun ProductListResult.reduce(state: ProductListState): ProductListState {
        return when (this) {
            is FetchProductsResult -> {
                state.copy(
                    isLoading = isLoading,
                    products = products,
                    error = error
                )
            }
            else -> state
        }
    }

    override fun Flow<ProductListResult>.toEffects(): Flow<ProductListEffect> {
        return merge(
            filterIsInstance<ProductClickedResult>()
                .map { result -> ProductClickedEffect(result.id) }
        )
    }

    private fun Flow<FetchProductsEvent>.toFetchProductsResults(): Flow<ProductListResult> {
        return flatMapLatest { repository.products() }
            .map { productsResult ->
                FetchProductsResult(
                    isLoading = productsResult is ProductsResult.Transient,
                    products = productsResult.products,
                    error = (productsResult as? ProductsResult.Error)?.throwable
                )
            }
    }

    private fun Flow<ProductClickedEvent>.toProductClickedResults(): Flow<ProductListResult> {
        return onEach { event -> logger.d("Clicked product with id: ${event.id}") }
            .map { event -> ProductClickedResult(event.id) }
    }

    class Factory @AssistedInject constructor(
        private val repository: ProductListRepository,
        private val logger: Logger,
        @Assisted owner: SavedStateRegistryOwner,
        @Assisted private val initialState: ProductListState
    ) : AbstractSavedStateViewModelFactory(owner, null) {
        override fun <T : ViewModel?> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            @Suppress("UNCHECKED_CAST")
            return ProductListViewModel(repository, logger, handle, initialState) as T
        }

        @AssistedFactory
        interface Factory {
            fun create(
                owner: SavedStateRegistryOwner,
                initialState: ProductListState
            ): ProductListViewModel.Factory
        }
    }
}
