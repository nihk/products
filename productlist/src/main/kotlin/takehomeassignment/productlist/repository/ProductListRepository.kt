package takehomeassignment.productlist.repository

import kotlinx.coroutines.flow.Flow
import takehomeassignment.productlist.state.ProductsState

interface ProductListRepository {
    fun products(): Flow<ProductsState>
}