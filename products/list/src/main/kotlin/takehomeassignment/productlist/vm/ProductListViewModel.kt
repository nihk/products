package takehomeassignment.productlist.vm

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import takehomeassignment.localproducts.models.Product
import takehomeassignment.productlist.repository.ProductListRepository
import takehomeassignment.productlist.state.ProductsState

class ProductListViewModel(
    repository: ProductListRepository,
    private val handle: SavedStateHandle
) : ViewModel() {

    private val productsState = MutableSharedFlow<ProductsState>(replay = 1)

    val products: Flow<List<Product>> = productsState.map { state -> state.products }
        .filterNotNull()

    val loading: Flow<Boolean> = productsState.map { state -> state is ProductsState.Loading }

    val error: Flow<Throwable?> = productsState.map { state ->
        (state as? ProductsState.Error)?.throwable
    }

    init {
        repository.products()
            .onEach(productsState::emit)
            .launchIn(viewModelScope)
    }

    class Factory @Inject constructor(private val repository: ProductListRepository) {
        fun create(owner: SavedStateRegistryOwner): AbstractSavedStateViewModelFactory {
            return object : AbstractSavedStateViewModelFactory(owner, null) {
                override fun <T : ViewModel?> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle,
                ): T {
                    @Suppress("UNCHECKED_CAST")
                    return ProductListViewModel(repository, handle) as T
                }
            }
        }
    }
}
