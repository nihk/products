package takehomeassignment.productlist.repository

import kotlinx.coroutines.flow.Flow
import takehomeassignment.productlist.models.ProductsPacket

internal interface ProductListRepository {
    fun products(): Flow<ProductsPacket>
}
