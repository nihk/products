package loblaw.productlist.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
    vmFactory: ProductListViewModel.Factory
): Fragment(R.layout.product_list_fragment) {

    private val viewModel by viewModels<ProductListViewModel> { vmFactory }
    private lateinit var onProductClicked: OnProductClicked
    private val adapter by lazy { ProductListAdapter(onProductClicked) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onProductClicked = context as OnProductClicked
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(ProductListFragmentBinding.bind(view)) {
            recyclerView.addItemDecoration(MarginItemDecoration(resources.getDimension(R.dimen.item_outer_gap).roundToInt()))
            recyclerView.adapter = adapter
            observeViewModel(this)
        }
    }

    private fun observeViewModel(binding: ProductListFragmentBinding) {
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visible()
            } else {
                binding.progressBar.gone()
            }
        }

        viewModel.products.observe(viewLifecycleOwner) { products ->
            adapter.submitList(products)
        }

        viewModel.error.observe(viewLifecycleOwner) { throwable ->
            if (throwable != null && adapter.isEmpty) {
                binding.errorMessage.visible()
            } else {
                binding.errorMessage.gone()
            }
        }
    }
}