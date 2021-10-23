package takehomeassignment.productlist.vm

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import takehomeassignment.NoOpLogger
import takehomeassignment.productlist.models.ProductsResult
import takehomeassignment.productlist.models.ViewState
import takehomeassignment.productlist.repository.ProductListRepository
import takehomeassignment.testutils.CoroutinesTestRule
import takehomeassignment.utils.products

class ProductListViewModelTest {
    @get:Rule
    val rule = CoroutinesTestRule()

    @Test
    fun `viewModel emits state from repository`() = rule.dispatcher.runBlockingTest {
        val repository = object : ProductListRepository {
            override fun products(): Flow<ProductsResult> {
                return flowOf(ProductsResult.Fresh(products))
            }
        }
        val viewModel = ProductListViewModel(repository, NoOpLogger(), SavedStateHandle(), ViewState())

        val viewState = viewModel.viewStates.first()

        assertEquals(products, viewState.products)
    }
}
