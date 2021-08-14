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
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import takehomeassignment.productlist.repository.ProductListRepository
import takehomeassignment.productlist.repository.ProductsResult

class ProductListViewModel(
    private val repository: ProductListRepository,
    private val handle: SavedStateHandle
) : ViewModel() {
    private val events = MutableSharedFlow<Event>()
    val productsStates = events.flatMapLatest { event -> handleEvent(event) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = ProductsViewState.Loading()
        )

    init {
        fetchProducts()
    }

    fun fetchProducts() {
        viewModelScope.launch {
            events.emit(Event.FetchProducts)
        }
    }

    private fun handleEvent(event: Event): Flow<ProductsViewState> {
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
