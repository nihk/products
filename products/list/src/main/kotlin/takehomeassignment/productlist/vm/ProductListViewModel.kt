package takehomeassignment.productlist.vm

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import takehomeassignment.core.Logger
import takehomeassignment.productlist.models.FetchProductsEvent
import takehomeassignment.productlist.models.FetchProductsResult
import takehomeassignment.productlist.models.ProductClickedEffect
import takehomeassignment.productlist.models.ProductClickedEvent
import takehomeassignment.productlist.models.ProductClickedResult
import takehomeassignment.productlist.models.ViewEffect
import takehomeassignment.productlist.models.ViewEvent
import takehomeassignment.productlist.models.ViewResult
import takehomeassignment.productlist.models.ViewState
import takehomeassignment.productlist.repository.ProductListRepository
import takehomeassignment.productlist.repository.ProductsResult

class ProductListViewModel(
    private val repository: ProductListRepository,
    private val logger: Logger,
    private val handle: SavedStateHandle,
    initialState: ViewState
) : ViewModel() {
    val viewStates: Flow<ViewState>
    val viewEffects: Flow<ViewEffect>
    private val viewEvents = MutableSharedFlow<ViewEvent>()

    init {
        viewEvents.toViewResults()
            .shareIn( // Only emit once per event in the `also` block below
                scope = viewModelScope,
                started = SharingStarted.Eagerly
            )
            .also { viewResults ->
                viewStates = viewResults.toViewStates(initialState)
                    .distinctUntilChanged()
                    .shareIn(
                        scope = viewModelScope,
                        started = SharingStarted.Eagerly,
                        replay = 1 // Cache the most recent state
                    )
                viewEffects = viewResults.toViewEffects()
            }

        processEvent(FetchProductsEvent)
    }

    fun processEvent(viewEvent: ViewEvent) {
        viewModelScope.launch {
            viewEvents.emit(viewEvent)
        }
    }

    private fun Flow<ViewEvent>.toViewResults(): Flow<ViewResult> {
        return merge(
            filterIsInstance<FetchProductsEvent>().toFetchProductsResults(),
            filterIsInstance<ProductClickedEvent>().toProductClickedResults()
        )
    }

    private fun Flow<FetchProductsEvent>.toFetchProductsResults(): Flow<ViewResult> {
        return flatMapLatest { repository.products() }
            .map { productsResult ->
                FetchProductsResult(
                    isCached = productsResult is ProductsResult.Cached,
                    products = productsResult.products,
                    error = (productsResult as? ProductsResult.Error)?.throwable
                )
            }
    }

    private fun Flow<ProductClickedEvent>.toProductClickedResults(): Flow<ViewResult> {
        return onEach { event -> logger.d("Clicked product with id: ${event.id}") }
            .map { event -> ProductClickedResult(event.id) }
    }

    private fun Flow<ViewResult>.toViewStates(initialState: ViewState): Flow<ViewState> {
        return scan(initialState) { viewState, viewResult ->
            when (viewResult) {
                is FetchProductsResult -> {
                    viewState.copy(
                        isLoading = viewResult.isCached,
                        products = viewResult.products,
                        error = viewResult.error
                    )
                }
                else -> viewState // No-op
            }
        }
    }

    private fun Flow<ViewResult>.toViewEffects(): Flow<ViewEffect> {
        return filterIsInstance<ProductClickedResult>()
            .map { result -> ProductClickedEffect(result.id) }
    }

    class Factory @AssistedInject constructor(
        private val repository: ProductListRepository,
        private val logger: Logger,
        @Assisted owner: SavedStateRegistryOwner
    ) : AbstractSavedStateViewModelFactory(owner, null) {
        override fun <T : ViewModel?> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            @Suppress("UNCHECKED_CAST")
            return ProductListViewModel(repository, logger, handle, ViewState()) as T
        }

        @AssistedFactory
        interface Factory {
            fun create(owner: SavedStateRegistryOwner): ProductListViewModel.Factory
        }
    }
}
