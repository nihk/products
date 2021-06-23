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
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn
import takehomeassignment.productlist.repository.ProductListRepository

class ProductListViewModel(
    repository: ProductListRepository,
    private val handle: SavedStateHandle
) : ViewModel() {

    val productsStates = repository.products()
        .stateIn(scope = viewModelScope, started = SharingStarted.Lazily, initialValue = null)
        .filterNotNull()

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
