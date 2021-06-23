package takehomeassignment.productlist.vm

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import takehomeassignment.localproducts.models.Product
import takehomeassignment.productlist.repository.ProductListRepository
import takehomeassignment.productlist.repository.ProductsResult
import takehomeassignment.testutils.CoroutinesTestRule

class ProductListViewModelTest {
    @get:Rule
    val rule = CoroutinesTestRule()

    @Test
    fun `viewModel emits state from repository`() = rule.dispatcher.runBlockingTest {
        val products = emptyList<Product>()
        val repository = object : ProductListRepository {
            override fun products(): Flow<ProductsResult> {
                return flowOf(ProductsResult.Fresh(products))
            }
        }
        val viewModel = ProductListViewModel(repository, SavedStateHandle())

        val productsState = viewModel.productsStates.first()

        assertTrue(productsState is ProductsViewState.Success)
    }
}
