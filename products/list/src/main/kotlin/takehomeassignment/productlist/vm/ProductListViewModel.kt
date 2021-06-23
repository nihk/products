package takehomeassignment.productlist.vm

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import takehomeassignment.productlist.repository.ProductListRepository
import takehomeassignment.productlist.repository.ProductsResult

class ProductListViewModel(
    repository: ProductListRepository,
    private val handle: SavedStateHandle
) : ViewModel() {

    val productsStates = repository.products()
        .map { result ->
            when (result) {
                is ProductsResult.Cached -> ProductsViewState.Loading(result.products)
                is ProductsResult.Fresh -> ProductsViewState.Success(result.products)
                is ProductsResult.Error -> ProductsViewState.Error(result.throwable, result.products)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = ProductsViewState.Loading()
        )

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
