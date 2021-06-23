package takehomeassignment.productlist.repository

import kotlinx.coroutines.flow.Flow

interface ProductListRepository {
    fun products(): Flow<ProductsResult>
}
