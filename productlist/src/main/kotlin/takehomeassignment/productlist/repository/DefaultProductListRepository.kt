package takehomeassignment.productlist.repository

import kotlinx.coroutines.flow.*
import takehomeassignment.core.Logger
import takehomeassignment.localproducts.dao.ProductsDao
import takehomeassignment.productlist.state.ProductsState
import takehomeassignment.remoteproducts.services.ProductsService
import javax.inject.Inject

class DefaultProductListRepository @Inject constructor(
    private val service: ProductsService,
    private val dao: ProductsDao,
    private val logger: Logger
) : ProductListRepository {

    override fun products(): Flow<ProductsState> = flow {
        emit(ProductsState.Loading())
        emit(ProductsState.Loading(dao.queryAll().first()))

        val flow = try {
            val products = service.cart().entries.toLocalProducts()
            dao.nukeThenInsert(products)
            dao.queryAll().map { ProductsState.Success(it) }
        } catch (throwable: Throwable) {
            logger.e("Failed to get products", throwable)
            dao.queryAll().map { ProductsState.Error(throwable, it) }
        }

        emitAll(flow)
    }
}