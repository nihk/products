package loblaw.productlist.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import loblaw.localproducts.models.Product
import javax.inject.Inject

class DefaultProductListRepository @Inject constructor(

) : ProductListRepository {
    override fun products(): Flow<List<Product>> {
        return emptyFlow()
    }
}