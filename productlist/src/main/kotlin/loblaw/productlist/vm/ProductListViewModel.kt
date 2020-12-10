package loblaw.productlist.vm

import androidx.lifecycle.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import loblaw.localproducts.models.Product
import loblaw.productlist.repository.ProductListRepository
import loblaw.productlist.state.ProductsState
import javax.inject.Inject

class ProductListViewModel(
    repository: ProductListRepository
) : ViewModel() {

    private val productsState = MutableLiveData<ProductsState>()

    val products: LiveData<List<Product>> = MediatorLiveData<List<Product>>().apply {
        addSource(productsState) { state ->
            if (state.products != null) {
                value = state.products
            }
        }
    }

    val loading: LiveData<Boolean> = productsState.map { state -> state is ProductsState.Loading }

    val error: LiveData<Throwable?> = productsState.map { state ->
        if (state is ProductsState.Error) {
            state.throwable
        } else {
            null
        }
    }

    init {
        repository.products()
            .onEach { state -> productsState.value = state }
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