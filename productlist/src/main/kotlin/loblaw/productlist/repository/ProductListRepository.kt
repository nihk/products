package loblaw.productlist.repository

import kotlinx.coroutines.flow.Flow
import loblaw.productlist.state.ProductsState

interface ProductListRepository {
    fun products(): Flow<ProductsState>
}