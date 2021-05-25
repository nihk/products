package takehomeassignment.productlist.vm

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import takehomeassignment.productlist.repository.ProductListRepository
import takehomeassignment.productlist.state.ProductsState
import takehomeassignment.testutils.CoroutinesTestRule

class ProductListViewModelTest {
    @get:Rule
    val rule = CoroutinesTestRule()

    @Test
    fun `viewModel emits state from repository`() = rule.dispatcher.runBlockingTest {
        val repository = object : ProductListRepository {
            override fun products(): Flow<ProductsState> {
                return flowOf(ProductsState.Loading())
            }
        }
        val viewModel = ProductListViewModel(repository, SavedStateHandle())

        val productsState = viewModel.productsStates.first()

        assertTrue(productsState is ProductsState.Loading)
    }
}
