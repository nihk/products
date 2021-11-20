package takehomeassignment.productlist.repository

import kotlinx.coroutines.flow.Flow
import takehomeassignment.productlist.models.ProductsPacket

interface ProductListRepository {
    fun products(): Flow<ProductsPacket>
}
