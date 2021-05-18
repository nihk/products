package takehomeassignment.productdetail.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import takehomeassignment.localproducts.dao.ProductsDao

class ProductDetailViewModel(
    private val dao: ProductsDao,
    private val id: String
) : ViewModel() {

    val product = flow { emit(dao.queryById(id)) }
        .stateIn(scope = viewModelScope, started = SharingStarted.Lazily, initialValue = null)
        .filterNotNull()

    class Factory @Inject constructor(private val dao: ProductsDao) {
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
