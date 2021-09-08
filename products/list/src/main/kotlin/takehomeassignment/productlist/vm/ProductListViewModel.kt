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
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import takehomeassignment.productlist.repository.ProductListRepository
import takehomeassignment.productlist.repository.ProductsResult

class ProductListViewModel(
    private val repository: ProductListRepository,
    private val handle: SavedStateHandle
) : ViewModel() {
    private val events = MutableSharedFlow<Event>()
    val productsStates = events
        .onStart { emit(Event.FetchProducts) }
        .flatMapLatest { event -> processEvent(event) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ProductsViewState.Loading()
        )

    fun fetchProducts() {
        sendEvent(Event.FetchProducts)
    }

    private fun sendEvent(event: Event) {
        viewModelScope.launch {
            events.emit(event)
        }
    }

    private fun processEvent(event: Event): Flow<ProductsViewState> {
        return when (event) {
            Event.FetchProducts -> repository.products()
                .map { result -> result.toProductsViewState() }
        }
    }

    private fun ProductsResult.toProductsViewState(): ProductsViewState {
        return when (this) {
            is ProductsResult.Cached -> ProductsViewState.Loading(products)
            is ProductsResult.Fresh -> ProductsViewState.Success(products)
            is ProductsResult.Error -> ProductsViewState.Error(throwable, products)
        }
    }

    private enum class Event {
        FetchProducts
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
            return ProductListViewModel(repository, handle) as T
        }

        @AssistedFactory
        interface Factory {
            fun create(owner: SavedStateRegistryOwner): ProductListViewModel.Factory
        }
    }
}
