package loblaw.productlist.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import loblaw.localproducts.models.Product
import loblaw.productlist.repository.ProductListRepository
import loblaw.productlist.state.ProductsState
import javax.inject.Inject

class ProductListViewModel(
    repository: ProductListRepository
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

    class Factory @Inject constructor(
        private val repository: ProductListRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ProductListViewModel(repository) as T
        }
    }
}