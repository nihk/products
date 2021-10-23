package takehomeassignment.productlist.repository

import kotlinx.coroutines.flow.Flow
import takehomeassignment.productlist.models.ProductsResult

interface ProductListRepository {
    fun products(): Flow<ProductsResult>
}
