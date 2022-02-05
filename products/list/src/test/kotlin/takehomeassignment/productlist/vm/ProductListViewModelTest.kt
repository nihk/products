package takehomeassignment.productlist.vm

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.TestScope
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import takehomeassignment.NoOpLogger
import takehomeassignment.productlist.models.FetchProductsEvent
import takehomeassignment.productlist.models.ProductClickedEffect
import takehomeassignment.productlist.models.ProductClickedEvent
import takehomeassignment.productlist.models.ProductListEffect
import takehomeassignment.productlist.models.ProductListState
import takehomeassignment.productlist.models.ProductsPacket
import takehomeassignment.productlist.repository.ProductListRepository
import takehomeassignment.testutils.CoroutinesTestRule
import takehomeassignment.utils.products

class ProductListViewModelTest {
    @get:Rule
    val rule = CoroutinesTestRule()

    @Test
    fun `should emit product states when products are fetched`() = viewModel(
        packets = flowOf(ProductsPacket.Fresh(products))
    ) {
        fetchProducts()

        assertStates(
            ProductListState(),
            ProductListState().copy(isLoading = true),
            ProductListState().copy(products = products),
        )
    }

    @Test
    fun `should handle cached when one is emitted while fetching`() = viewModel(
        packets = flowOf(ProductsPacket.Cached(products))
    ) {
        fetchProducts()

        assertStates(
            ProductListState(),
            ProductListState().copy(isLoading = true),
            ProductListState().copy(isLoading = true, products = products),
        )
    }

    @Test
    fun `should handle error when one is thrown while fetching`() {
        val throwable: Throwable = RuntimeException("Uh oh!")
        viewModel(
            packets = flowOf(ProductsPacket.Error(throwable = throwable))
        ) {
            fetchProducts()

            assertStates(
                ProductListState(),
                ProductListState().copy(isLoading = true),
                ProductListState().copy(error = throwable),
            )
        }
    }

    @Test
    fun `should emit product clicked effect when product is clicked`() = viewModel {
        clickProduct(id = "1234")

        assertEffects(ProductClickedEffect(id = "1234"))
    }

    private fun viewModel(
        packets: Flow<ProductsPacket> = emptyFlow(),
        block: ProductListViewModelRobot.() -> Unit
    ) {
        ProductListViewModelRobot(
            packets = packets,
            scope = TestScope(rule.dispatcher)
        )
            .block()
    }

    internal class ProductListViewModelRobot(
        packets: Flow<ProductsPacket>,
        scope: CoroutineScope
    ) {
        private val repository = object : ProductListRepository {
            override fun products(): Flow<ProductsPacket> {
                return packets
            }
        }
        private val viewModel = ProductListViewModel(
            repository = repository,
            logger = NoOpLogger(),
            handle = SavedStateHandle()
        )
        private val collectedStates = mutableListOf<ProductListState>()
        private val collectedEffects = mutableListOf<ProductListEffect>()

        init {
            merge(
                viewModel.states.onEach(collectedStates::add),
                viewModel.effects.onEach(collectedEffects::add)
            ).launchIn(scope)
        }

        fun fetchProducts() {
            viewModel.processEvent(FetchProductsEvent)
        }

        fun clickProduct(id: String) {
            viewModel.processEvent(ProductClickedEvent(id = id))
        }

        fun assertStates(vararg expectation: ProductListState) {
            assertEquals(expectation.size, collectedStates.size)
            expectation.zip(collectedStates) { expected, collected ->
                assertEquals(expected, collected)
            }
        }

        fun assertEffects(vararg expectation: ProductListEffect) {
            assertEquals(expectation.size, collectedEffects.size)
            expectation.zip(collectedEffects) { expected, collected ->
                assertEquals(expected, collected)
            }
        }
    }
}
