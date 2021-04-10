package takehomeassignment.productlist.vm

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import takehomeassignment.productlist.repository.ProductListRepository
import takehomeassignment.productlist.state.ProductsState
import javax.inject.Inject

class ProductListViewModel(
    repository: ProductListRepository,
    private val handle: SavedStateHandle
) : ViewModel() {

    private val productsStates = MutableStateFlow<ProductsState?>(null)
    fun productsStates(): Flow<ProductsState> = productsStates.filterNotNull()

    init {
        repository.products()
            .onEach(productsStates::emit)
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
