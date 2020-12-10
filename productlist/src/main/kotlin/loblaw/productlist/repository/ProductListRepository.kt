package loblaw.productlist.repository

import kotlinx.coroutines.flow.Flow
import loblaw.localproducts.models.Product

interface ProductListRepository {
    fun products(): Flow<List<Product>>
}