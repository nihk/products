package takehomeassignment.productlist.repository

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import takehomeassignment.core.Logger
import takehomeassignment.localproducts.dao.ProductsDao
import takehomeassignment.productlist.models.ProductsPacket
import takehomeassignment.remoteproducts.services.ProductsService

class DefaultProductListRepository @Inject constructor(
    private val service: ProductsService,
    private val dao: ProductsDao,
    private val logger: Logger
) : ProductListRepository {

    override fun products(): Flow<ProductsPacket> = flow {
        emit(ProductsPacket.Cached(dao.queryAll().first()))

        val flow = try {
            val products = service.cart().entries.toLocalProducts()
            dao.nukeThenInsert(products)
            dao.queryAll().map { ProductsPacket.Fresh(it) }
        } catch (throwable: Throwable) {
            logger.e("Failed to get products", throwable)
            dao.queryAll().map { ProductsPacket.Error(throwable, it) }
        }

        emitAll(flow)
    }
}
