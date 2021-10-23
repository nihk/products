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
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import takehomeassignment.productlist.models.FetchProductsEvent
import takehomeassignment.productlist.models.FetchProductsResult
import takehomeassignment.productlist.models.ViewEvent
import takehomeassignment.productlist.models.ViewResult
import takehomeassignment.productlist.models.ViewState
import takehomeassignment.productlist.repository.ProductListRepository
import takehomeassignment.productlist.repository.ProductsResult

class ProductListViewModel(
    private val repository: ProductListRepository,
    private val handle: SavedStateHandle,
    initialState: ViewState
) : ViewModel() {
    val viewStates: Flow<ViewState>
    private val viewEvents = MutableSharedFlow<ViewEvent>()

    init {
        viewEvents.toViewResults()
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                replay = 1
            )
            .also { viewResult ->
                viewStates = viewResult.toViewStates(initialState)
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
            filterIsInstance<FetchProductsEvent>().toFetchProductsResults()
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
            }
        }
    }

    class Factory @AssistedInject constructor(
        private val repository: ProductListRepository,
        @Assisted owner: SavedStateRegistryOwner
    ) : AbstractSavedStateViewModelFactory(owner, null) {
        override fun <T : ViewModel?> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            @Suppress("UNCHECKED_CAST")
            return ProductListViewModel(repository, handle, ViewState()) as T
        }

        @AssistedFactory
        interface Factory {
            fun create(owner: SavedStateRegistryOwner): ProductListViewModel.Factory
        }
    }
}
