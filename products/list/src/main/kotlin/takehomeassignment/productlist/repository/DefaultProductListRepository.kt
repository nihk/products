package takehomeassignment.productlist.repository

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import takehomeassignment.core.Logger
import takehomeassignment.localproducts.dao.ProductsDao
import takehomeassignment.localproducts.models.Product
import takehomeassignment.productlist.models.ViewResult
import takehomeassignment.remoteproducts.services.ProductsService

class DefaultProductListRepository @Inject constructor(
    private val service: ProductsService,
    private val dao: ProductsDao,
    private val logger: Logger
) : ProductListRepository {

    override fun products(): Flow<ProductsResult> = flow {
        emit(ProductsResult.Cached(dao.queryAll().first()))

        val flow = try {
            val products = service.cart().entries.toLocalProducts()
            dao.nukeThenInsert(products)
            dao.queryAll().map { ProductsResult.Fresh(it) }
        } catch (throwable: Throwable) {
            logger.e("Failed to get products", throwable)
            dao.queryAll().map { ProductsResult.Error(throwable, it) }
        }

        emitAll(flow)
    }
}

sealed class ProductsResult {
    abstract val products: List<Product>
    data class Cached(override val products: List<Product>) : ProductsResult()
    data class Fresh(override val products: List<Product>) : ProductsResult()
    data class Error(val throwable: Throwable, override val products: List<Product>) : ProductsResult()
}
