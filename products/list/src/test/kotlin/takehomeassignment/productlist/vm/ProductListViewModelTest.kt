package takehomeassignment.productlist.vm

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.test.runBlockingTest
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
    fun `should emit product states when products are fetched`() = rule.dispatcher.runBlockingTest {
        val repository = object : ProductListRepository {
            override fun products(): Flow<ProductsPacket> {
                return flowOf(ProductsPacket.Fresh(products))
            }
        }
        val viewModel = ProductListViewModel(repository, NoOpLogger(), SavedStateHandle(), ProductListState(), emptyList())

        val states = mutableListOf<ProductListState>()
        viewModel.states
            .onEach { states += it }
            .takeWhile { state -> state.products == null }
            .launchIn(this)

        viewModel.processEvent(FetchProductsEvent)

        val initialState = ProductListState()
        val secondState = ProductListState().copy(isLoading = true)
        val thirdState = ProductListState().copy(products = products)
        assertEquals(3, states.size)
        assertEquals(states[0], initialState)
        assertEquals(states[1], secondState)
        assertEquals(states[2], thirdState)
    }

    @Test
    fun `should emit product clicked effect when product is clicked`() = rule.dispatcher.runBlockingTest {
        val repository = object : ProductListRepository {
            override fun products(): Flow<ProductsPacket> {
                return flowOf(ProductsPacket.Fresh(products))
            }
        }
        val viewModel = ProductListViewModel(repository, NoOpLogger(), SavedStateHandle(), ProductListState(), emptyList())

        val effects = mutableListOf<ProductListEffect>()
        viewModel.effects
            .onEach { effect -> effects += effect }
            .takeWhile { effect -> effect !is ProductClickedEffect }
            .launchIn(this)

        viewModel.processEvent(ProductClickedEvent(id = "1234"))

        assertEquals(1, effects.size)
        assertEquals(ProductClickedEffect(id = "1234"), effects.first())
    }
}
