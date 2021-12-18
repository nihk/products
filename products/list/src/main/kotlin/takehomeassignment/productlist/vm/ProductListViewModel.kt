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
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import takehomeassignment.core.Logger
import takehomeassignment.productlist.models.FetchProductsEvent
import takehomeassignment.productlist.models.FetchProductsResult
import takehomeassignment.productlist.models.ProductClickedEffect
import takehomeassignment.productlist.models.ProductClickedEvent
import takehomeassignment.productlist.models.ProductClickedResult
import takehomeassignment.productlist.models.ProductListEffect
import takehomeassignment.productlist.models.ProductListEvent
import takehomeassignment.productlist.models.ProductListResult
import takehomeassignment.productlist.models.ProductListState
import takehomeassignment.productlist.models.ProductsPacket
import takehomeassignment.productlist.models.StartLoadingResult
import takehomeassignment.productlist.repository.ProductListRepository
import takehomeassignment.utils.mvi.MviViewModel

class ProductListViewModel(
    private val repository: ProductListRepository,
    private val logger: Logger,
    private val handle: SavedStateHandle,
    initialState: ProductListState,
    private val initialEvents: List<ProductListEvent>
) : MviViewModel<ProductListEvent, ProductListResult, ProductListState, ProductListEffect>(
    initialState
) {

    override fun onStart() {
        initialEvents.forEach(::processEvent)
    }

    override fun Flow<ProductListEvent>.toResults(): Flow<ProductListResult> {
        return merge(
            filterIsInstance<FetchProductsEvent>().toFetchProductsResults(),
            filterIsInstance<ProductClickedEvent>().toProductClickedResults()
        )
    }

    override fun ProductListResult.reduce(state: ProductListState): ProductListState {
        return when (this) {
            is StartLoadingResult -> state.copy(isLoading = true)
            is FetchProductsResult -> state.copy(
                isLoading = isCached,
                products = products,
                error = error
            )
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
            .mapLatest<ProductsPacket, ProductListResult> { packet ->
                FetchProductsResult(
                    isCached = packet is ProductsPacket.Cached,
                    products = packet.products,
                    error = (packet as? ProductsPacket.Error)?.throwable
                )
            }
            .onStart { emit(StartLoadingResult) }
    }

    private fun Flow<ProductClickedEvent>.toProductClickedResults(): Flow<ProductListResult> {
        return onEach { event -> logger.d("Clicked product with id: ${event.id}") }
            .map { event -> ProductClickedResult(event.id) }
    }

    class Factory @AssistedInject constructor(
        private val repository: ProductListRepository,
        private val logger: Logger,
        @Assisted owner: SavedStateRegistryOwner,
        @Assisted private val initialState: ProductListState,
        @Assisted private val initialEvents: List<ProductListEvent>
    ) : AbstractSavedStateViewModelFactory(owner, null) {
        override fun <T : ViewModel?> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            @Suppress("UNCHECKED_CAST")
            return ProductListViewModel(repository, logger, handle, initialState, initialEvents) as T
        }

        @AssistedFactory
        interface Factory {
            fun create(
                owner: SavedStateRegistryOwner,
                initialState: ProductListState,
                initialEvents: List<ProductListEvent>
            ): ProductListViewModel.Factory
        }
    }
}
