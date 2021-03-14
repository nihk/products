package takehomeassignment.productdetail.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import takehomeassignment.localproducts.dao.ProductsDao
import takehomeassignment.localproducts.models.Product
import javax.inject.Inject

class ProductDetailViewModel(
    private val dao: ProductsDao,
    private val id: String
) : ViewModel() {

    private val product = MutableStateFlow<Product?>(null)
    fun product(): Flow<Product> = product.filterNotNull()

    init {
        viewModelScope.launch {
            product.value = dao.queryById(id)
        }
    }

    class Factory @Inject constructor(
        private val dao: ProductsDao
    ) {
        fun create(id: String): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return ProductDetailViewModel(dao, id) as T
                }
            }
        }
    }
}