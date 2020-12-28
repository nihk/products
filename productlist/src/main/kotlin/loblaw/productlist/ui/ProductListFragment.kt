package loblaw.productlist.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import loblaw.productlist.R
import loblaw.productlist.databinding.ProductListFragmentBinding
import loblaw.productlist.vm.ProductListViewModel
import loblaw.uiutils.MarginItemDecoration
import loblaw.uiutils.gone
import loblaw.uiutils.isEmpty
import loblaw.uiutils.visible
import javax.inject.Inject
import kotlin.math.roundToInt

class ProductListFragment @Inject constructor(
    vmFactory: ProductListViewModel.Factory,
    private val onProductClicked: OnProductClicked,
    private val imageLoader: ImageLoader
): Fragment(R.layout.product_list_fragment) {

    private val viewModel by viewModels<ProductListViewModel> { vmFactory }
    private val adapter by lazy { ProductListAdapter(onProductClicked, imageLoader) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(ProductListFragmentBinding.bind(view)) {
            recyclerView.addItemDecoration(MarginItemDecoration(resources.getDimension(R.dimen.item_outer_gap).roundToInt()))
            recyclerView.adapter = adapter
            observeViewModel(this@with)
        }
    }

    private fun observeViewModel(binding: ProductListFragmentBinding) {
        viewModel.loading
            .onEach { isLoading ->
                if (isLoading) {
                    binding.progressBar.visible()
                } else {
                    binding.progressBar.gone()
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.products
            .onEach { products ->
                adapter.submitList(products)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.error
            .onEach { throwable ->
                if (throwable != null && adapter.isEmpty) {
                    binding.errorMessage.visible()
                } else {
                    binding.errorMessage.gone()
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }
}