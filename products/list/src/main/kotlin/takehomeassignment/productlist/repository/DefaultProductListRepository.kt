package takehomeassignment.productlist.repository

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import takehomeassignment.core.Logger
import takehomeassignment.localproducts.dao.ProductsDao
import takehomeassignment.localproducts.models.Product
import takehomeassignment.productlist.models.ProductsPacket
import takehomeassignment.productlist.models.RemoteProduct
import takehomeassignment.remoteproducts.services.ProductsService

internal class DefaultProductListRepository(
    private val service: ProductsService,
    private val dao: ProductsDao,
    private val logger: Logger
) : ProductListRepository {

    override fun products(): Flow<ProductsPacket> {
        return flow {
            emit(State.Loading)

            try {
                val products = service.cart().entries
                emit(State.Success(products))
            } catch (throwable: Throwable) {
                if (throwable is CancellationException) throw throwable
                logger.e("Failed to get products", throwable)
                emit(State.Error(throwable))
            }
        }.flatMapLatest { state ->
            val productItems = dao.queryAll().map(List<Product>::toProductItems)

            when (state) {
                State.Loading -> productItems.map(ProductsPacket::Cached)
                is State.Success -> {
                    val latestProducts = state.remoteProducts.toLocalProducts()
                    dao.nukeThenInsert(latestProducts)
                    productItems.map(ProductsPacket::Fresh)
                }
                is State.Error -> productItems.map { products -> ProductsPacket.Error(state.throwable, products) }
            }
        }
    }

    private sealed class State {
        object Loading : State()
        data class Success(val remoteProducts: List<RemoteProduct>) : State()
        data class Error(val throwable: Throwable) : State()
    }
}
